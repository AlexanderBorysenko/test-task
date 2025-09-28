import { Component, OnDestroy, OnInit, computed, inject, signal } from '@angular/core';
import { ItemsService } from './items.service';
import { Item } from '../types/IItem';
import { ItemsHeaderComponent } from './components/items-header/items-header.component';
import { ItemsMetricsComponent } from './components/items-metrics/items-metrics.component';
import { ItemsTableComponent } from './components/items-table/items-table.component';
import { InlineAlertComponent } from './components/inline-alert/inline-alert.component';

@Component({
    selector: 'app-root',
    imports: [ItemsHeaderComponent, ItemsMetricsComponent, ItemsTableComponent, InlineAlertComponent],
    templateUrl: './app.html',
})
export class App implements OnInit, OnDestroy {
    private readonly itemsService = inject(ItemsService);

    readonly items = signal<Item[]>([]);
    readonly isCreating = signal(false);
    readonly isInitialLoading = signal(true);
    readonly listError = signal<string | null>(null);
    readonly createError = signal<string | null>(null);
    readonly lastUpdatedAt = signal<Date | null>(null);

    readonly pendingCount = computed(
        () => this.items().filter((item) => item.status === 'PENDING').length
    );
    readonly doneCount = computed(
        () => this.items().filter((item) => item.status === 'DONE').length
    );

    protected async setupItems(): Promise<void> {
        try {
            const items = await this.itemsService.getItems();
            this.items.set(items);
            this.listError.set(null);
        } catch (error) {
            this.listError.set('Unable to load items. Please try again later.');
        } finally {
            this.isInitialLoading.set(false);
        }
    }

    protected readonly itemsRefreshInterval = setInterval(async () => {
        try {
            const items: Item[] = await this.itemsService.getItems();
            this.items.set(items);

            this.lastUpdatedAt.set(new Date());
            this.listError.set(null);

        } catch (error) {
            this.listError.set('Unable to load items. We will retry automatically.');
            return;
        } finally {
            this.isInitialLoading.set(false);
        }
    }, 2000);

    ngOnInit(): void {
        this.setupItems();
    }
    ngOnDestroy(): void {
        clearInterval(this.itemsRefreshInterval);
    }

    async addItem(): Promise<void> {
        if (this.isCreating()) return;

        this.createError.set(null);
        this.isCreating.set(true);

        try {
            const item = await this.itemsService.createItem();
            this.items.update((items) => [item, ...items.filter((existing) => existing.id !== item.id)]);
            this.isCreating.set(false);
        } catch (error) {
            this.createError.set('Something went wrong while creating the item. Please try again.');
            this.isCreating.set(false);
        }
    }
}
