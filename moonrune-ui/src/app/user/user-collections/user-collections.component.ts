import { Component } from '@angular/core';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';
import { CollectionService } from '../../general/collection.service';
import { TermCollection } from '../../terms/termcollection';
import { NgFor, NgIf } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Cookie } from 'ng2-cookies';

@Component({
  selector: 'app-user-collections',
  standalone: true,
  imports: [MainRoutingComponent, NgFor, NgIf, RouterLink],
  templateUrl: './user-collections.component.html',
  styleUrl: './user-collections.component.css'
})
export class UserCollectionsComponent {

  constructor(private collectionService: CollectionService, private route: ActivatedRoute, private router: Router) {}

  collections ?: TermCollection[]
  username: string = String(this.route.snapshot.paramMap.get('username'))
  visiting ?: string

  ngOnInit() {
    if (Cookie.check('username')) this.visiting = Cookie.get('username');
    this.collectionService.getCollectionsByOwner(this.username).subscribe(collections => this.collections = collections)
  }

  createCollection() {
    this.collectionService.createCollection(this.username).subscribe(id => {
      this.router.navigate(['/collection', id])
    })
  }

}
