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
  collectionInfo : TermCollection = {collectionID: 0, owner: "", name: "", description: "", accessLevel: 0}
  name: String = ""
  desc: String = ""
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
    this.collectionService.getCollectionInfo(this.id).subscribe(collectionInfo => {
      this.collectionInfo = collectionInfo
      this.name = collectionInfo.name
      this.desc = collectionInfo.description
  })
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
    if (term.meanings.length == 0) term.meanings = [""]
  }

  saveChanges() {

  }

  startEdit() {
    this.editMode = true;
    this.editModeChange.emit(this.editMode)
  }

  cancelEdit() {
    this.editMode = false;
    this.editModeChange.emit(this.editMode)
  }

  confirmEdit() {
    this.editMode = false;
    this.editModeChange.emit(this.editMode)
  }
}
