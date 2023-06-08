import React from 'react';
import ReactPaginate from "react-paginate";

interface CatalogPaginationProps {
    pages: number;
    changePage: (page: number) => void;
}

export function CatalogPagination(props: CatalogPaginationProps) {
    const handlePageClick = (event: any) => {
        props.changePage(event.selected);
    };

    return (
        <ReactPaginate
            nextLabel="Следующая >"
            onPageChange={handlePageClick}
            pageRangeDisplayed={5}
            marginPagesDisplayed={3}
            pageCount={props.pages}
            previousLabel="< Предыдущая"
            pageClassName="page-item"
            pageLinkClassName="page-link"
            previousClassName="page-item"
            previousLinkClassName="page-link"
            nextClassName="page-item"
            nextLinkClassName="page-link"
            breakLabel="..."
            breakClassName="page-item"
            breakLinkClassName="page-link"
            containerClassName="pagination"
            activeClassName="active"
            renderOnZeroPageCount={undefined}
        />
    )
}