import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Item } from '../types/IItem';

@Injectable({
    providedIn: 'root'
})
export class ItemsService {
    private readonly http = inject(HttpClient);
    private readonly itemsEndpoint = `http://localhost:8080/items`;

    public async getItems(): Promise<Item[]> {
        return (await firstValueFrom(this.http.get<Item[]>(this.itemsEndpoint))).reverse();
    }

    public async createItem(): Promise<Item> {
        return (await firstValueFrom(this.http.post<Item>(this.itemsEndpoint, null)));
    }
}
