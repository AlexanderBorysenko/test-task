import { Component, input, output } from '@angular/core';

@Component({
    selector: 'app-items-header',
    templateUrl: './items-header.component.html',
})
export class ItemsHeaderComponent {
    readonly isCreating = input(false);
    readonly createRequested = output<void>();

    onCreateClick(): void {
        if (this.isCreating()) {
            return;
        }

        this.createRequested.emit();
    }
}
