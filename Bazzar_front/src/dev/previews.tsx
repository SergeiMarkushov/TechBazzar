import React from 'react';
import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox';
import {PaletteTree} from './palette';
import {Orders} from "../view/profile/orders/Orders";
import {OrderTab} from "../view/profile/orders/OrderTab";
import {emptyOrderNew} from "../empty";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/Orders">
                <Orders/>
            </ComponentPreview>
            <ComponentPreview path="/OrderTab">
                <OrderTab onReloadOrder={() => {}} order={emptyOrderNew}/>
            </ComponentPreview>
            <ComponentPreview path="/ComponentPreviews">
                <ComponentPreviews/>
            </ComponentPreview>
        </Previews>
    );
};

export default ComponentPreviews;