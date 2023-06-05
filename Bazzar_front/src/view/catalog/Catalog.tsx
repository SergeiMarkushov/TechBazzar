import React, {useEffect, useState} from "react";
import {CatalogCard} from "./CatalogCard";
import {CatalogFilter} from "./CatalogFilter";
import {CatalogPagination} from "./CatalogPagination";
import {apiDeleteProductById, apiGetProductsNew} from "../../api/ProductApi";
import {AxiosResponse} from "axios";
import {useSearch} from "../../context/Search";
import {defaultFilter, emptyProductNew} from "../../empty";
import {FilterNew, FindRequest, PageProductNew} from "../../newInterfaces";
import {CircularLoading} from "../CircularLoading";

const DEFAULT_PAGE = 0;
const DEFAULT_PAGES = 1;

export interface CatalogProps {
    isChanging?: boolean,
}

export function Catalog(props: CatalogProps) {
    const [products, setProducts] = useState(Array.of(emptyProductNew))
    const [filterNew, setFilter] = useState(defaultFilter)
    const [page, setPage] = useState(DEFAULT_PAGE)
    const [pages, setPages] = useState(DEFAULT_PAGES)
    const [limit] = useState(20)
    const [load, setLoad] = useState(false)
    let changing = props.isChanging === undefined ? false : props.isChanging;
    let search = useSearch();

    function filterChanged(filter: FilterNew) {
        setFilter(filter)
        setPage(DEFAULT_PAGE)
    }

    function deleteHandler(id: number) {
        apiDeleteProductById(id).then(r => {
                if (r.status === 200) {
                    setProducts(products.filter(product => product.id !== id));
                }
            }
        )
    }

    useEffect(() => {
        console.log("useEffect")
            const queryParams: FindRequest = {
                p: page,
            };

            if (filterNew.maxPrice !== defaultFilter.maxPrice) {
                queryParams.max_price = filterNew.maxPrice;
            }
            if (filterNew.minPrice !== defaultFilter.minPrice) {
                queryParams.min_price = filterNew.minPrice;
            }
            if (search.search !== "") {
                queryParams.title_part = search.search;
            }
            if (limit !== 20) {
                /*queryParams.limit = limit;*/
            }

            apiGetProductsNew(queryParams)
                .then((page: AxiosResponse<PageProductNew>) => {
                    console.log(page.data)
                    console.log(queryParams)
                    if (page.data !== undefined) {
                        setLoad(true);
                        setProducts(page.data.items);

                        if (page.data.totalPages !== undefined) {
                            setPages(page.data.totalPages);
                        }
                    }
                })
        }, [filterNew, page, search.search]
    );


    function changePage(page: number) {
        setPage(page);
        if (document.scrollingElement !== null) {
            document.scrollingElement.scrollTop = 0;
        }
    }

    return (
        <div>
            <div className="d-flex row">
                <div className="flex-grow-0" style={{maxWidth: "80px"}}>
                    <CatalogFilter filterHandler={filterChanged} filter={filterNew}/>
                </div>
                <div className="container-fluid flex-grow-1">
                    <div className="row justify-content-center">
                        {load ?
                            products.map((product) => (
                                <CatalogCard product={product} deleteHandler={changing ? deleteHandler : undefined}
                                             isChanging={changing} key={product.id}></CatalogCard>
                            ))
                            : <CircularLoading/>
                        }
                    </div>
                </div>
            </div>
            { pages > 1 &&
                <CatalogPagination changePage={changePage} pages={pages}/>
            }
        </div>
    );
}