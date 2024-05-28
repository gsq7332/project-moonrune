import { NgFor, NgIf } from '@angular/common';
import { Component, Input, Output, SimpleChanges } from '@angular/core';
import { ListEntryComponent } from '../list-entry/list-entry.component';
import { Term } from '../../../terms/term';
import { TermDetailsComponent } from '../../../general/term-details/term-details.component';
import { CollectionService } from '../../../general/collection.service';

@Component({
  selector: 'app-terms-list',
  standalone: true,
  imports: [NgFor, NgIf, ListEntryComponent, TermDetailsComponent],
  templateUrl: './terms-list.component.html',
  styleUrl: './terms-list.component.css'
})
export class TermsListComponent {
  constructor(private collectionService: CollectionService){}

  selectedTerm?: Term;
  @Input() id ?: number
  @Input() editMode ?: boolean
  

  terms: Term[] = [];

  ngOnInit(): void {
    this.getTerms();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['id']) {
      this.getTerms();
    }
  }

  onSelect(term: Term): void {
    this.selectedTerm = term;
  }

  getTerms(): void {
    if (this.id == undefined) return;
    this.collectionService.getTerms(this.id).subscribe(terms => this.terms = terms);
  }
  
}
