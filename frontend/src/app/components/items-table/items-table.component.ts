import { Component, computed, input } from '@angular/core';
import { DatePipe } from '@angular/common';

import { Item } from '../../../types/IItem';
import { StatusBadgeComponent } from '../status-badge/status-badge.component';

@Component({
    selector: 'app-items-table',
    imports: [DatePipe, StatusBadgeComponent],
    templateUrl: './items-table.component.html',
})
export class ItemsTableComponent {
    readonly items = input.required<Item[]>();
    readonly isInitialLoading = input<boolean>(false);
    readonly lastUpdatedAt = input<Date | null>(null);
    readonly error = input<string | null>(null);

    protected readonly hasItems = computed(() => this.items().length > 0);
}