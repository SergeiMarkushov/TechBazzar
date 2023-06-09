import axios, { AxiosResponse } from 'axios';
import {ReviewNew} from "./newInterfaces";
export const getComments: ReviewNew[] = [
    {
        id: 1,
        username: 'Vova',
        reviewText: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam aliquet turpis at fringilla efficitur. Curabitur rutrum ligula at risus ullamcorper, ac dignissim elit dignissim.',
        mark: 5
    },
    {
        id: 2,
        username: 'John',
        reviewText: 'Vestibulum nec commodo massa. Fusce laoreet fringilla elit, nec iaculis justo luctus vitae. Suspendisse luctus risus id lectus condimentum, at laoreet odio gravida.',
        mark: 3.4
    },
    {
        id: 3,
        username: 'Emily',
        reviewText: 'Sed ut massa ligula. Duis et nibh a est malesuada eleifend. Nam vitae nunc auctor, aliquam est nec, luctus justo. Ut tincidunt facilisis purus, eget auctor dolor cursus sed.',
        mark: 4.5
    },
    {
        id: 4,
        username: 'Alex',
        reviewText: 'Quisque cursus volutpat orci, at lobortis metus vulputate eget. Aenean pellentesque nunc sit amet est semper, vitae consequat elit efficitur. Sed sagittis venenatis orci a placerat. Phasellus non augue id erat eleifend commodo.',
        mark: 2.5
    },
    {
        id: 5,
        username: 'Olivia',
        reviewText: 'Maecenas hendrerit risus sit amet felis pellentesque, non dapibus tellus semper. Nam pharetra arcu id mi interdum consequat. Vestibulum sit amet feugiat lorem. Suspendisse euismod dolor mi, in gravida risus aliquet sit amet.',
        mark: 1.5
    },
    {
        id: 6,
        username: 'Liam',
        reviewText: 'Sed ut massa ligula. Duis et nibh a est malesuada eleifend. Nam vitae nunc auctor, aliquam est nec, luctus justo. Ut tincidunt facilisis purus, eget auctor dolor cursus sed.',
        mark: 4.5
    }
    ];


interface Pictures {
    download_url: string;
}

interface Product {
    name: string;
    description: string;
    price: number;
    image: string;
}

class RandomProduct {
    private static randomProducts: string[] = [
        "lemon", "apple", "orange", "banana", "pineapple", "watermelon", "grapefruit", "grapes", "strawberry",
        "raspberry", "blueberry", "blackberry", "kiwi", "pear", "peach", "plum", "apricot", "cherry", "mango",
        "coconut", "avocado", "tomato", "potato", "cucumber", "onion", "garlic", "carrot", "broccoli", "cauliflower",
        "spinach", "lettuce", "cabbage", "celery", "asparagus", "mushroom", "peas", "beans", "corn", "sweet potato",
        "eggplant", "zucchini", "pumpkin", "squash", "beetroot", "radish", "turnip", "ginger", "garlic", "leek",
        "cress", "artichoke", "olive", "pepper", "chili"
    ];

    public static async getRandomProduct(size: number): Promise<Product[]> {
        const pictures: Pictures[] = await RandomProduct.getPictures(size);
        const products: Product[] = [];

        for (let i = 0, p = 0; i < size; ++i, p++) {
            if (p >= pictures.length) {
                p = 0;
            }

            products.push({
                name: RandomProduct.randomString(5),
                description: RandomProduct.randomString(150),
                price: RandomProduct.randomPrice(1000),
                image: pictures[p].download_url
            });
        }

        return products;
    }

    public static async getNotRandomProduct(): Promise<Product[]> {
        const pictures: Pictures[] = await RandomProduct.getPictures(RandomProduct.randomProducts.length);
        const products: Product[] = [];

        for (let i = 0; i < RandomProduct.randomProducts.length; ++i) {
            products.push({
                name: RandomProduct.randomProducts[i],
                description: `Description_${i}`,
                price: RandomProduct.randomPrice(1000),
                image: pictures[i].download_url
            });
        }

        return products;
    }

    private static async getPictures(size: number): Promise<Pictures[]> {
        const url = `https://picsum.photos/v2/list?page=2&limit=${size}`;
        const response: AxiosResponse<Pictures[]> = await axios.get<Pictures[]>(url);
        return response.data;
    }

    private static randomString(length: number): string {
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        let result = '';

        for (let i = 0; i < length; i++) {
            const randomIndex: number = Math.floor(Math.random() * chars.length);
            result += chars.charAt(randomIndex);
        }

        return result;
    }

    private static randomPrice(max: number): number {
        return Math.random() * max;
    }
}
