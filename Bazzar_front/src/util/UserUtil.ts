import {UserNew} from "../newInterfaces";

export function getUserNew(): UserNew | null {
    const user = localStorage.getItem('user');
    if (user !== null) {
        return JSON.parse(user);
    }
    return null;
}

export function setUserNew(user: UserNew): void {
    localStorage.setItem('user', JSON.stringify(user));
}

export function deleteUser(): void {
    localStorage.removeItem('user');
}