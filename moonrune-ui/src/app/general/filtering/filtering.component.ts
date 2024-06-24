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
  @Input() filter : filters = {
    matching: "",
      isDiacritic: 0,
      grades: [],
      jlpt: [],
      strokes: [0, 0],
      frequnecy: [0, 0]
  }
  @Output() filterChange = new EventEmitter<filters>()

  updateGrades(grade: string) {
    let idx = this.filter.grades.indexOf(grade)
    if (idx == undefined) {
      this.filter.grades.push(grade)
    } else {
      this.filter.grades.splice(idx, idx)
    }
  }

  updateJlpt(jlpt: string) {
    let idx = this.filter.grades.indexOf(jlpt)
    if (idx == undefined) {
      this.filter.grades.push(jlpt)
    } else {
      this.filter.grades.splice(idx, idx)
    }
  }

  sendFilters() {
    this.filterChange.emit(this.filter)
  }

}
