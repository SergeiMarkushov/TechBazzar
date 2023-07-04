import {
    CartItem,
    Cart, Characteristic,
    Discount,
    Filter,
    OrderItem, Organization, OrganizationCreate, ProductForCreate,
    Product,
    Review,
    User
} from "./newInterfaces";

export const emptyCharacteristic: Characteristic = {
    id: null,
    name: "",
    product: null,
}
export const emptyCart: Cart = {
    totalPrice: 0,
    items: Array.of(),
}

export const emptyCartItemsNew: CartItem = {
    productId: 0,
    productTitle: "",
    quantity: 0,
    pricePerProduct: 0,
    price: 0
}

export const defaultFilter: Filter = {
    maxPrice: 2147483647,
    minPrice: 1,
    organizationTitle: "",
}
export const emptyDiscountNew: Discount = {
    id: 0,
    dis: 0,
    startDate: new Date(),
    expiryDate: new Date(),
}

export const emptyReviewNew: Review = {
    id: 0,
    username: "",
    fullName: "",
    reviewText: "",
    mark: 0,
}

export const emptyProductNew: Product = {
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
    pictureId: 1,
}

export const defaultUserNew: User = {
    id: -1,
    username: "",
    balance: 0,
    active: true,
}

export const emptyOrderItemNew: OrderItem = {
    id: 0,
    productTitle: "",
    productId: 0,
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

export const emptyProductCreateNew2: ProductForCreate = {
    title: '',
    description: '',
    organizationTitle: '',
    price: 0,
    quantity: 0,
    characteristicsDto: Array.of(),
    pictureId: 1
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