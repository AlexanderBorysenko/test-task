export type ItemStatus = 'PENDING' | 'DONE';

export interface Item {
    id: string;
    status: ItemStatus;
}
