import {AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {ErrorComponent} from "../../ErrorComponent";
import {apiDeleteProductById, apiGetProductsNew} from "../../api/ProductApi";
import {useError} from "../../auth/ErrorProvider";
import {useSearch} from "../../context/Search";
import {defaultFilter, emptyProductNew} from "../../empty";
import {Filter, FindRequest, PageProduct} from "../../newInterfaces";
import {CircularLoading} from "../CircularLoading";
import {BuyerFilter} from "./BuyerFilter";
import {CatalogCard} from "./CatalogCard";
import {CatalogPagination} from "./CatalogPagination";
import {PriceFilter} from "./PriceFilter";

const DEFAULT_PAGE = 0;
const DEFAULT_PAGES = 1;

export interface CatalogProps {
    isChanging?: boolean,
    companyTitle?: string,
}

export function Catalog(props: CatalogProps) {
    const [products, setProducts] = useState(Array.of(emptyProductNew))
    const [filter, setFilter] = useState(defaultFilter)
    const [page, setPage] = useState(DEFAULT_PAGE)
    const [pages, setPages] = useState(DEFAULT_PAGES)
    const [limit] = useState(20)
    const [load, setLoad] = useState(false)
    const changing = props.isChanging === undefined ? false : props.isChanging;
    const search = useSearch();
    const {companyParam} = useParams();
    const error = useError();

    function filterChanged(filter: Filter) {
        setFilter(filter)
        setPage(DEFAULT_PAGE)
    }

    function deleteHandler(id: number) {
        apiDeleteProductById(id).then(() => {
                setProducts(products.filter(product => product.id !== id));
            }
        ).catch(() => {
            error.setErrors("Не удалось удалить", true, false, "");
            error.setShow(true)
        })
    }


    useEffect(() => {
        const queryParams: FindRequest = {
            p: page + 1,
        };

        if (filter.maxPrice !== defaultFilter.maxPrice) {
            queryParams.max_price = filter.maxPrice;
        }
        if (filter.minPrice !== defaultFilter.minPrice) {
            queryParams.min_price = filter.minPrice;
        }
        if (filter.organizationTitle !== defaultFilter.organizationTitle) {
            queryParams.organization_title = filter.organizationTitle;
        }
        if (search.search !== "") {
            queryParams.title_part = search.search;
        }
        if (props.companyTitle !== undefined) {
            queryParams.organization_title = props.companyTitle;
        }
        if (companyParam !== undefined) {
            queryParams.organization_title = companyParam;
        }
        if (limit !== 20) {
            queryParams.limit = limit;
        }
        getProducts(queryParams);

    }, [filter.maxPrice, filter.minPrice, filter.organizationTitle, limit, page, search.search]);


    function getProducts(queryParams: FindRequest) {
        apiGetProductsNew(queryParams)
            .then((page: AxiosResponse<PageProduct>) => {
                if (page.data !== undefined) {
                    setLoad(true);
                    setProducts(page.data.items);

                    if (page.data.totalPages !== undefined) {
                        setPages(page.data.totalPages);
                    }

                    if (page.data.items.length === 0) {
                        error.setErrors("Ничего не найдено", false, false, "");
                        error.setShow(true)
                        return;
                    }
                    error.setErrors("", true, false, "");
                }
            }).catch(() => {
            error.setErrors("Упс... Что то пошло не так. Попробуйте обновить страницу", false, false, "");
            error.setShow(true)
        })
    }

    function changePage(page: number) {
        setPage(page);
        if (document.scrollingElement !== null) {
            document.scrollingElement.scrollTop = 0;
        }
    }

    return (
        <div>

            <div>
                <div className="container-fluid">
                    <div className="row justify-content-start">
                        <div className="col-1">
                            <PriceFilter filterHandler={filterChanged} filter={filter}/>
                        </div>
                        {!props.companyTitle && !companyParam &&
                            <div className="col-2">
                                <BuyerFilter filterHandler={filterChanged} filter={filter}/>
                            </div>
                        }
                    </div>
                    {error.success &&
                        <div className="container-fluid flex-grow-1">
                            <div className="row justify-content-center">
                                {load ?
                                    products.map((product) => (
                                        <CatalogCard product={product}
                                                     deleteHandler={changing ? deleteHandler : undefined}
                                                     isChanging={changing} key={product.id}></CatalogCard>
                                    ))
                                    : <CircularLoading/>
                                }
                            </div>
                        </div>
                    }
                </div>
                <div className="ms-3">
                    {pages > 1 &&
                        <CatalogPagination changePage={changePage} pages={pages}/>
                    }
                </div>
            </div>
        </div>
    );
}