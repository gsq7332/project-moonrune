import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SearchBarComponent } from '../../dictionary/search-bar/search-bar.component';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { filters } from '../../terms/filters';

@Component({
  selector: 'app-filtering',
  standalone: true,
  imports: [SearchBarComponent, NgIf, FormsModule, NgFor],
  templateUrl: './filtering.component.html',
  styleUrl: './filtering.component.css'
})
export class FilteringComponent {
  @Input() collectionID ?: number;
  @Input() isGame: boolean = false;
  LEGAL_GRADES = ["1", "2", "3", "4", "5", "6", "S"]
  LEGAL_JLPT = ["5", "4", "3", "2", "1"]
  gradeValues = [true, true, true, true, true, true, true]
  jlptValues = [true, true, true, true, true]
  @Input() filter : filters = {
    matching: "",
      isDiacritic: 0,
      grades: [],
      jlpt: [],
      strokes: [0, 0],
      frequency: [0, 0]
  }
  @Output() filterChange = new EventEmitter<filters>()

  updateGrades() {
    this.filter.grades = []
    for (let i = 0; i < this.gradeValues.length; i++) {
      if (this.gradeValues[i])
      this.filter.grades.push(this.LEGAL_GRADES[i])
    }
    this.sendFilters()
  }

  updateJlpt() {
    this.filter.grades = []
    for (let i = 0; i < this.jlptValues.length; i++) {
      if (this.jlptValues[i])
      this.filter.grades.push(this.LEGAL_JLPT[i])
    }
    this.sendFilters()
  }

  sendFilters() {
    this.filterChange.emit(this.filter)
  }

}
