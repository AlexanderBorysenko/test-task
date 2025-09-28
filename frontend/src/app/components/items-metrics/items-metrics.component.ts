import { Component, input } from '@angular/core';

@Component({
    selector: 'app-items-metrics',
    templateUrl: './items-metrics.component.html',
})
export class ItemsMetricsComponent {
    readonly total = input.required<number>();
    readonly pending = input.required<number>();
    readonly done = input.required<number>();
}
