export function getGuestId(): string | null {
    let guestId = localStorage.getItem('guestId');
    if (guestId !== null) {
        return guestId;
    }
    return null;
}

export function setGuestId(guestId: string): void {
    localStorage.setItem('guestId', guestId);
}

export function deleteGuestId(): void {
    localStorage.removeItem('guestId');
}