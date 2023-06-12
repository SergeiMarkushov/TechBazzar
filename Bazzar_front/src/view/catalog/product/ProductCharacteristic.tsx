import React from "react";
import {ProductNew} from "../../../newInterfaces";

export interface ProductCharacteristicProps {
    product: ProductNew
}

export function ProductCharacteristic(props: ProductCharacteristicProps) {
    return (
        <div className="card border-0">
            <div className="card-body">
                <h5 className="card-title">Характеристики</h5>
                <div className="row">
                    <div className="col">
                        <div className="d-flex row">
                            {
                                props.product.characteristicsDto.map((characteristic, index) => {
                                    return (
                                        <span key={index}>- {characteristic.name}</span>
                                    )
                                })
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}