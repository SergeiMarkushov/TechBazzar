export interface Product {
    id?: number;
    title: string;
    description: string;
    organizationTitle: string;
    price: number;
    quantity: number;
    isConfirmed: boolean;
    discount: Discount|null;
    review: Review|null;
    characteristicsDto: Array<Characteristic>;
    pictureId : number;
}

export interface ProductForCreate {
    id?: number;
    title?: string;
    description?: string;
    organizationTitle?: string;
    price?: number;
    quantity?: number;
    characteristicsDto?: Array<string>;
    pictureId?: number;
}

export interface ProductForCreate2 {
    id?: number;
    title?: string;
    description?: string;
    organizationTitle?: string;
    price?: number;
    quantity?: number;
    characteristicsDto?: Array<Characteristic>;
    pictureId?: number;
}

export interface Characteristic {
    id: null | number;
    name: string;
    product: null;
}

export interface Discount {
    id: number;
    dis: number;
    startDate: Date;
    expiryDate: Date;
}

export interface DiscountCreate {
    id: number;
    products: Array<Product>;
    discount: number;
    startDate: Date;
    expiryDate: Date;
}

export interface Review {
    id: number;
    username: string;
    reviewText: string;
    mark: number;
}

export interface PageProduct {
    items: Array<Product>;
    page: number;
    totalPages: number;
}

export interface Cart {
    totalPrice: number;
    items: Array<CartItem>;
}

export interface CartItem {
    productId: number;
    productTitle: string;
    quantity: number;
    pricePerProduct: number;
    price: number;
}

export interface Order {
    id: number;
    username: string;
    items: Array<OrderItem>;
    totalPrice: number;
    createdAt: string;
    status: boolean;
}

export interface OrderItem {
    id: number;
    productTitle: string;
    productId: number;
    orderId: number;
    quantity: number;
    pricePerProduct: number;
    price: number;
}

export interface User {
    id: number;
    username: string;
    balance: number;
    active: boolean;
}

export interface Filter {
    minPrice: number;
    maxPrice: number;
    organizationTitle: string;
}

export interface PurchaseHistory {
    id: number;
    email: string;
    productTitle: string;
    organizationTitle: string;
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

export interface ReviewDto {
    username: string;
    reviewText: string;
    mark: number;
    productId: number;
}