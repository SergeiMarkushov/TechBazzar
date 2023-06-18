import jwtDecode from "jwt-decode";

export function getToken(): string {
    try {
        if (typeof window !== 'undefined' && window.localStorage) {
            const token = localStorage.getItem('token');
            return token ?? '';
        }
    } catch (error) {
        // eslint-disable-next-line no-console
        console.error('Не удалось получить токен из localStorage:', error);
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

export function isTokenValid(): boolean {
    try {
        const token: string = getToken();
        if (token !== '') {
            const decodedToken: { exp: number } = jwtDecode(token);
            return decodedToken.exp > Date.now() / 1000;
        }
    } catch (error) {
        // eslint-disable-next-line no-console
        console.error('Не удалось проверить токен:', error);
    }
    return false;
}