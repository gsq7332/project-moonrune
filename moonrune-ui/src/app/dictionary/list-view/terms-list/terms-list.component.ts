import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { ListEntryComponent } from '../list-entry/list-entry.component';
import { TermServiceService } from '../../../term-service.service';
import { Term } from '../../../terms/term';
import { TermDetailsComponent } from '../../../general/term-details/term-details.component';

@Component({
  selector: 'app-terms-list',
  standalone: true,
  imports: [NgFor, NgIf, ListEntryComponent, TermDetailsComponent],
  templateUrl: './terms-list.component.html',
  styleUrl: './terms-list.component.css'
})
export class TermsListComponent {
  constructor(private termsService: TermServiceService){}

  selectedTerm?: Term;

  terms: Term[] = [];

  ngOnInit(): void {
    this.getTerms();
  }

  onSelect(term: Term): void {
    this.selectedTerm = term;
  }

  getTerms(): void {
    this.termsService.getTerms().subscribe(terms => this.terms = terms);
  }
  
}
