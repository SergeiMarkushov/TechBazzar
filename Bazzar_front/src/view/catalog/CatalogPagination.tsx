import React from 'react';
import ReactPaginate from "react-paginate";

interface CatalogPaginationProps {
    pages: number;
    changePage: (page: number) => void;
}

export function CatalogPagination(props: CatalogPaginationProps) {
    const handlePageClick = (event: {selected: number}) => {
        props.changePage(event.selected);
    };

    return (
        <ReactPaginate
            nextLabel=">"
            onPageChange={handlePageClick}
            pageRangeDisplayed={5}
            marginPagesDisplayed={3}
            pageCount={props.pages}
            previousLabel="<"
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