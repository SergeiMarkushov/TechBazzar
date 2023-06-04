import jwtDecode from "jwt-decode";

export function getToken(): string {
    let token = localStorage.getItem('token');
    if (token !== null) {
        return token;
    }
    return '';
}

export function setToken(token: string): void {
    localStorage.setItem('token', token);
    // @ts-ignore
    localStorage.setItem('userRoles', jwtDecode(token).roles);
}

export function getRoles(): string[] {
    let roles: string | null = localStorage.getItem('userRoles');
    let rolesArray: string[] = [];
    if (roles !== null) {
        rolesArray = roles.split(',');
        return rolesArray;
    }
    return [];
}

export function deleteToken(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userRoles');
}

export function isTokenExist(): boolean {
    return getToken() !== '';
}