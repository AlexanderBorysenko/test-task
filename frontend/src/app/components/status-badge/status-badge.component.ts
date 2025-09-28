import { Component, computed, input } from '@angular/core';
import { NgClass } from '@angular/common';
import { ItemStatus } from '../../../types/IItem';

@Component({
    selector: 'app-status-badge',
    imports: [NgClass],
    templateUrl: './status-badge.component.html',
})
export class StatusBadgeComponent {
    readonly status = input.required<ItemStatus>();

    protected readonly variantClasses = computed(() => {
        const status = this.status();

        if (status === 'PENDING') {
            return 'bg-amber-500/10 text-amber-200 ring-1 ring-inset ring-amber-500/30';
        }

        return 'bg-emerald-500/10 text-emerald-200 ring-1 ring-inset ring-emerald-500/30';
    });
}
