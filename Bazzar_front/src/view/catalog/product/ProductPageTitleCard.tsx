import {ProductCard} from "./ProductPage";

export function ProductPageTitleCard(props: ProductCard) {
    return (
        <div className="card mb-3 border-0">
            <div className="row g-0">
                <div className="col-md-4">
                    {/*TODO: replace this*/}
                    <img src="https://i.pinimg.com/originals/ae/8a/c2/ae8ac2fa217d23aadcc913989fcc34a2.png" className="img-fluid rounded-start"/>
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h5 className="card-title">{props.product.title}</h5>
                        <p className="card-text">Something features...</p>
                    </div>
                </div>
            </div>
        </div>
    )
}