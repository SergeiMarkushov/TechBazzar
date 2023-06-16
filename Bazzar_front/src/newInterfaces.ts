export interface ProductNew {
    id?: number;
    title: string;
    description: string;
    organizationTitle: string;
    price: number;
    quantity: number;
    isConfirmed: boolean;
    discount: DiscountNew|null;
    review: ReviewNew|null;
    characteristicsDto: Array<Characteristic>;
    pictureId : number;
}

export interface ProductCreateNew {
    id?: number;
    title: string;
    description: string;
    organizationTitle: string;
    price: number;
    quantity: number;
}

export interface ProductCreateNew2 {
    id?: number;
    title: string;
    description: string;
    organizationTitle: string;
    price: number;
    quantity: number;
    characteristicsDto: Array<string>;
    pictureId : number;
}

export interface Characteristic {
    id: null;
    name: string;
    product: null;
}

export interface DiscountNew {
    id: number;
    dis: number;
    startDate: Date;
    expiryDate: Date;
}

export interface DiscountCreateNew {
    id: number;
    products: Array<ProductNew>;
    discount: number;
    startDate: Date;
    expiryDate: Date;
}

export interface ReviewNew {
    id: number;
    username: string;
    reviewText: string;
    mark: number;
}

export interface PageProductNew {
    items: Array<ProductNew>;
    page: number;
    totalPages: number;
}

export interface CartNew {
    totalPrice: number;
    items: Array<CartItemNew>;
}

export interface CartItemNew {
    productId: number;
    productTitle: string;
    quantity: number;
    pricePerProduct: number;
    price: number;
}

export interface OrderNew {
    id: number;
    username: string;
    items: Array<OrderItemNew>;
    totalPrice: number;
    createdAt: string;
    status: boolean;
}

export interface OrderItemNew {
    id: number;
    productTitle: string;
    orderId: number;
    quantity: number;
    pricePerProduct: number;
    price: number;
}

export interface AuthResponseNew {
    token: string;
}

export interface UserNew {
    id: number;
    email: string;
    username: string;
    password?: string;
    balance: number;
    active: boolean;
}

export interface FilterNew {
    minPrice: number;
    maxPrice: number;
}

export interface PurchaseHistory {
    id: number;
    email: string;
    productTitle: string;
    organization: string;
    quantity: number;
    datePurchase: Date;
}

export interface FindRequest {
    p: number,
    max_price?: number,
    min_price?: number,
    limit?: number,
    title_part?: string,
    organization_title?: string,
}

export interface Role {
    id: number;
    name: string;
}

export interface Organization {
    id: number;
    title: string;
    description: string;
    owner: string;
    active: boolean;
}

export interface OrganizationCreate {
    name: string;
    description: string;
    owner: string;
    companyImage: File;
}

export interface ErrorMessage {
    message: string;
    code: number;
}

export interface Picture {
    bytes: string;
    contentType: string;
}