import { Component, Input, Output } from '@angular/core';
import { Term } from '../../terms/term';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { find } from 'rxjs';
import { EventEmitter } from '@angular/core';
import { CollectionService } from '../../general/collection.service';

@Component({
  selector: 'app-terms-edit',
  standalone: true,
  imports: [NgFor, FormsModule, NgIf],
  templateUrl: './terms-edit.component.html',
  styleUrl: './terms-edit.component.css'
})
export class TermsEditComponent {
  originalTerms ?: Term[]
  currentTerms ?: Term[]
  @Input() id ?: number
  @Input() editMode ?: boolean = false;
  @Output() editModeChange = new EventEmitter()

  constructor(private collectionService: CollectionService) {}

  addTerm() {
    let term: Term = {term: "", meanings: [""], id: 0}
    this.currentTerms?.push(term)
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

  startEdit() {
    this.editMode = true;
  }

  cancelEdit() {
    this.editMode = false;
  }

  confirmEdit() {
    this.editMode = false;
  }
}
