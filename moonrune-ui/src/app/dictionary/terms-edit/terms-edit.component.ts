import { Component, Input, Output } from '@angular/core';
import { Term } from '../../terms/term';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventEmitter } from '@angular/core';
import { CollectionService } from '../../general/collection.service';
import { TermCollection } from '../../terms/termcollection';
import { TermService } from '../../general/term.service';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';

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
  name: string = ""
  desc: string = ""
  @Input() id ?: number
  @Input() editMode : boolean = false;
  @Output() editModeChange = new EventEmitter<boolean>()

  constructor(private collectionService: CollectionService, private termService: TermService, private route: Router) {}

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

  delete() {
    if (this.id == undefined) return;
    let username = ""
    if (Cookie.check('username')) {
      username = Cookie.get('username')
      this.collectionService.deleteCollection(this.id).subscribe(_ => this.route.navigate(['/collections', username]))
    }
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
    if (this.id == undefined) return;
    this.termService.updateTerms(this.id, this.currentTerms).subscribe(terms => this.currentTerms = terms)
    this.collectionService.updateCollectionInfo(this.id, this.name, this.desc).subscribe(collectionInfo => this.collectionInfo = collectionInfo)
    this.editModeChange.emit(this.editMode)
  }
}
