import { TestBed } from '@angular/core/testing';

import { TermServiceService } from './term-service.service';

describe('TermServiceService', () => {
  let service: TermServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TermServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
