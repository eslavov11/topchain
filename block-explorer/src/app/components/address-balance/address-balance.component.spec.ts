import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddressBalanceComponent } from './address-balance.component';

describe('AddressBalanceComponent', () => {
  let component: AddressBalanceComponent;
  let fixture: ComponentFixture<AddressBalanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddressBalanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
