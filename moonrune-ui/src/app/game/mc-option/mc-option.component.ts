import { Component, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-mc-option',
  standalone: true,
  imports: [],
  templateUrl: './mc-option.component.html',
  styleUrl: './mc-option.component.css'
})
export class McOptionComponent {
  @Input() option ?: string;
  @Output() eventSelectOption = new EventEmitter();

  selectOption() {
    if (this.option == undefined) {
      this.eventSelectOption.emit("")
      return
    }
    this.eventSelectOption.emit(this.option)
  }
}
