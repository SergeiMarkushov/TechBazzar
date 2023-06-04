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
            nextLabel="next >"
            onPageChange={handlePageClick}
            pageRangeDisplayed={5}
            marginPagesDisplayed={3}
            pageCount={props.pages}
            previousLabel="< previous"
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