export function getTokenKeyCloak(): string {
    try {
        if (typeof window !== 'undefined' && window.localStorage) {
            const token = localStorage.getItem('tokenKeyCloak');
            return token ?? '';
        }
    } catch (error) {
        // eslint-disable-next-line no-console
        console.error('Не удалось получить токен из localStorage:', error);
    }
    return '';
}

export function setTokenKeyCloak(newToken: string): void {
    localStorage.setItem('tokenKeyCloak', newToken);
}