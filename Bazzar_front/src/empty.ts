import {
    CartItemNew,
    CartNew,
    DiscountNew,
    FilterNew,
    OrderItemNew, Organization, OrganizationCreate, ProductCreateNew,
    ProductNew,
    ReviewNew,
    UserNew
} from "./newInterfaces";

export let emptyCart: CartNew = {
    totalPrice: 0,
    items: Array.of(),
}

export let emptyCartItemsNew: CartItemNew = {
    productId: 0,
    productTitle: "",
    quantity: 0,
    pricePerProduct: 0,
    price: 0
}

export let defaultFilter: FilterNew = {
    maxPrice: 2147483647,
    minPrice: 1,
}
export let emptyDiscountNew: DiscountNew = {
    id: 0,
    dis: 0,
    startDate: new Date(),
    expiryDate: new Date(),
}

export let emptyReviewNew: ReviewNew = {
    id: 0,
    username: "",
    reviewText: "",
    mark: 0,
}

export let emptyProductNew: ProductNew = {
    id: 0,
    title: "",
    description: "",
    organizationTitle: "",
    price: 0,
    quantity: 0,
    isConfirmed: false,
    discount: emptyDiscountNew,
    review: emptyReviewNew,
}

export let defaultUserNew: UserNew = {
    id: -1,
    email: "",
    username: "",
    balance: 0,
    active: true,
}

export let emptyOrderItemNew: OrderItemNew = {
    id: 0,
    productTitle: "",
    orderId: 0,
    quantity: 0,
    pricePerProduct: 0,
    price: 0,
}

export let emptyOrderNew = {
    id: 0,
    username: "",
    items: Array.of(emptyOrderItemNew),
    totalPrice: 0,
    createdAt: "",
    status: false,
}

export let emptyProductCreateNew: ProductCreateNew = {
    title: '',
    description: '',
    organizationTitle: '',
    price: 0,
    quantity: 0
}

export let emptyRole = {
    id: 0,
    name: ""
}

export let emptyOrganization: Organization = {
    id: 0,
    title: "",
    description: "",
    owner: "",
    active: false,
}

export let emptyOrganizationCreate: OrganizationCreate = {
    name: "",
    description: "",
    owner: "",
    companyImage: new File([], ""),
}