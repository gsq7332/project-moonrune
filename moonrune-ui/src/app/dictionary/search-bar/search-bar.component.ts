import { Component, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent {
  @Input() searchTerm: string = ""
  @Output() searchTermChange = new EventEmitter<string>();

  changeSearchTerm() {
    this.searchTermChange.emit(this.searchTerm);
  }
}
