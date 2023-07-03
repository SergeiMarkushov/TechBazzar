import {PurchaseHistory} from "../../../newInterfaces";

interface PurchaseCardProps {
    purchase: PurchaseHistory
}

export function PurchaseCard({purchase}: PurchaseCardProps) {
    return (
        <div className="rounded shadow m-2 p-2 d-flex justify-content-between">
            <div>
                <div className="d-flex">
                    <h5>{purchase.productTitle}</h5>
                    <p> - {purchase.quantity} шт.</p>
                </div>
                <p>Продавец: {purchase.organizationTitle}</p>
            </div>
            <p>Дата покупки: {purchase.datePurchase.toString()}</p>
        </div>
    )
}