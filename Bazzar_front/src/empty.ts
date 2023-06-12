import {
    CartItemNew,
    CartNew, Characteristic,
    DiscountNew,
    FilterNew,
    OrderItemNew, Organization, OrganizationCreate, ProductCreateNew, ProductCreateNew2,
    ProductNew,
    ReviewNew,
    UserNew
} from "./newInterfaces";

export const emptyCharacteristic: Characteristic = {
    id: null,
    name: "",
    product: null,
}
export const emptyCart: CartNew = {
    totalPrice: 0,
    items: Array.of(),
}

export const emptyCartItemsNew: CartItemNew = {
    productId: 0,
    productTitle: "",
    quantity: 0,
    pricePerProduct: 0,
    price: 0
}

export const defaultFilter: FilterNew = {
    maxPrice: 2147483647,
    minPrice: 1,
}
export const emptyDiscountNew: DiscountNew = {
    id: 0,
    dis: 0,
    startDate: new Date(),
    expiryDate: new Date(),
}

export const emptyReviewNew: ReviewNew = {
    id: 0,
    username: "",
    reviewText: "",
    mark: 0,
}

export const emptyProductNew: ProductNew = {
    id: 0,
    title: "",
    description: "",
    organizationTitle: "",
    price: 0,
    quantity: 0,
    isConfirmed: false,
    discount: emptyDiscountNew,
    review: emptyReviewNew,
    characteristicsDto: Array.of(emptyCharacteristic),
}

export const defaultUserNew: UserNew = {
    id: -1,
    email: "",
    username: "",
    balance: 0,
    active: true,
}

export const emptyOrderItemNew: OrderItemNew = {
    id: 0,
    productTitle: "",
    orderId: 0,
    quantity: 0,
    pricePerProduct: 0,
    price: 0,
}

export const emptyOrderNew = {
    id: 0,
    username: "",
    items: Array.of(emptyOrderItemNew),
    totalPrice: 0,
    createdAt: "",
    status: false,
}

export const emptyProductCreateNew: ProductCreateNew = {
    title: '',
    description: '',
    organizationTitle: '',
    price: 0,
    quantity: 0
}

export const emptyProductCreateNew2: ProductCreateNew2 = {
    title: '',
    description: '',
    organizationTitle: '',
    price: 0,
    quantity: 0,
    characteristicsDto: Array.of(),
}

export const emptyRole = {
    id: 0,
    name: ""
}

export const emptyOrganization: Organization = {
    id: 0,
    title: "",
    description: "",
    owner: "",
    active: false,
}

export const emptyOrganizationCreate: OrganizationCreate = {
    name: "",
    description: "",
    owner: "",
    companyImage: new File([], ""),
}