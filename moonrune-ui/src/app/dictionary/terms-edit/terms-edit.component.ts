import { Component, Input } from '@angular/core';
import { Term } from '../../terms/term';
import { NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { find } from 'rxjs';

@Component({
  selector: 'app-terms-edit',
  standalone: true,
  imports: [NgFor, FormsModule],
  templateUrl: './terms-edit.component.html',
  styleUrl: './terms-edit.component.css'
})
export class TermsEditComponent {
  originalTerms ?: Term[]
  currentTerms ?: Term[]
  @Input() id ?: number

  addTerm() {
    let newTerm: Term = {term: "", meanings: []}
    this.currentTerms?.push(newTerm)
  }

  removeTerm(idx: number) {
    if (!this.currentTerms) return;
    this.currentTerms.splice(idx, idx)    
  }

  removeMeaning(idx: number) {
    if (!this.currentTerms) return;
    this.currentTerms?.splice(idx, idx)
  }

  saveChanges() {
    for (let term in this.originalTerms) {
      
    }
  }
}
