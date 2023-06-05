import {UserNew} from "../newInterfaces";

export function getUserNew(): UserNew | null {
    let user = localStorage.getItem('user');
    if (user !== null) {
        return JSON.parse(user);
    }
    return null;
}

export function setUser(user: UserNew): void {
    localStorage.setItem('user', JSON.stringify(user));
}

export function setUserNew(user: UserNew): void {
    localStorage.setItem('user', JSON.stringify(user));
}

export function deleteUser(): void {
    localStorage.removeItem('user');
}