import { Component, computed, input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
    selector: 'app-inline-alert',
    imports: [NgClass],
    templateUrl: './inline-alert.component.html',
})
export class InlineAlertComponent {
    readonly message = input.required<string>();
    readonly tone = input<'info' | 'success' | 'error'>('info');

    protected readonly toneClasses = computed(() => {
        switch (this.tone()) {
            case 'success':
                return 'border-emerald-500/30 bg-emerald-500/10 text-emerald-200';
            case 'error':
                return 'border-red-500/30 bg-red-500/10 text-red-200';
            default:
                return 'border-slate-500/30 bg-slate-500/10 text-slate-200';
        }
    });
}
