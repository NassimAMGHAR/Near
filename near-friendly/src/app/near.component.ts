import { Component, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'near-root',
  templateUrl: './near.component.html',
  styleUrls: ['./near.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NearComponent {
  title = 'Near view is now ! ';
}
