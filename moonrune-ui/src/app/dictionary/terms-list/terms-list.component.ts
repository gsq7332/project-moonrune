import { NgFor, NgIf } from '@angular/common';
import { Component, Input, SimpleChanges } from '@angular/core';
import { Term } from '../../terms/term';
import { TermDetailsComponent } from '../../general/term-details/term-details.component';
import { CollectionService } from '../../general/collection.service';
import { filters } from '../../terms/filters';

@Component({
  selector: 'app-terms-list',
  standalone: true,
  imports: [NgFor, NgIf, TermDetailsComponent],
  templateUrl: './terms-list.component.html',
  styleUrl: './terms-list.component.css'
})
export class TermsListComponent {
  constructor(private collectionService: CollectionService){}

  selectedTerm?: Term;
  @Input() id ?: number
  @Input() filter ?: filters
  

  terms: Term[] = [];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['id'] || changes['filter']) {
      this.getTerms();
    }
  }

  onSelect(term: Term): void {
    this.selectedTerm = term;
  }

  getTerms(): void {
    if (this.id == undefined) return;
      if (this.filter == undefined) {
      this.collectionService.getTerms(this.id).subscribe(terms => {
        this.terms = terms
    });
    }
    else {
      this.collectionService.getTermsWithFilter(this.id, this.filter).subscribe(terms => {
        this.terms = terms
    });
    }
  }
}
