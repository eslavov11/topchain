import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-address-balance',
  templateUrl: './address-balance.component.html',
  styleUrls: ['./address-balance.component.scss']
})
export class AddressBalanceComponent implements OnInit {
  address:string;
  constructor(private route:ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params=> 
      { 
        this.getBalance(params['address'])});


  }

  getBalance(address:string){
    
  }

}
