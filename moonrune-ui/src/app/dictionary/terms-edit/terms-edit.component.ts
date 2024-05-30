import { Component, Input, Output } from '@angular/core';
import { Term } from '../../terms/term';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventEmitter } from '@angular/core';
import { CollectionService } from '../../general/collection.service';
import { TermCollection } from '../../terms/termcollection';

@Component({
  selector: 'app-terms-edit',
  standalone: true,
  imports: [NgFor, FormsModule, NgIf],
  templateUrl: './terms-edit.component.html',
  styleUrl: './terms-edit.component.css'
})
export class TermsEditComponent {
  currentTerms : Term[] = []
  collectionInfo ?: TermCollection
  @Input() id ?: number
  @Input() editMode : boolean = false;
  @Output() editModeChange = new EventEmitter<boolean>()

  constructor(private collectionService: CollectionService) {}

  ngOnInit() {
    this.getTerms()
    this.getCollectionInfo()
  }

  getTerms() {
    if (this.id == undefined) return;
    this.collectionService.getTerms(this.id).subscribe(terms => this.currentTerms = terms)
  }

  getCollectionInfo() {
    if (this.id == undefined) return;
    this.collectionService.getCollectionInfo(this.id).subscribe(collectionInfo => this.collectionInfo = collectionInfo)
  }

  addTerm() {
    let term: Term = {term: "", meanings: [""], id: 0}
    this.currentTerms?.push(term)
  }

  addMeaning(term: Term) {
    term.meanings.push("");
  }

  removeTerm(idx: number) {
    this.currentTerms.splice(idx, idx)    
  }

  removeMeaning(term: Term, idx: number) {
    term.meanings.splice(idx, idx)
  }

  saveChanges() {

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
