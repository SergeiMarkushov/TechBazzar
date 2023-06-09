import jwtDecode from "jwt-decode";

export function getToken(): string {
    const token = localStorage.getItem('token');
    if (token !== null) {
        return token;
    }
    return '';
}

export function setToken(token: string): void {
    localStorage.setItem('token', token);
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    localStorage.setItem('userRoles', jwtDecode(token).roles);
}

export function getRoles(): string[] {
    const roles: string | null = localStorage.getItem('userRoles');
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