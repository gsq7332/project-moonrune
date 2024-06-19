import { Component, Input } from '@angular/core';
import { SearchBarComponent } from '../../dictionary/search-bar/search-bar.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-filtering',
  standalone: true,
  imports: [SearchBarComponent, NgIf],
  templateUrl: './filtering.component.html',
  styleUrl: './filtering.component.css'
})
export class FilteringComponent {
  @Input() collectionID ?: number;
  @Input() isGame: boolean = false;
  matching: string = "";
  isDiacritic: number = 0;
  grades: string[] = [];
  jlpt: string[] = [];
  strokes: number[] = [0, 0];
  frequency: number[] = [0, 0];

}
