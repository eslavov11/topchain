import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Topchain Explorer!';
  address:string;
  constructor(private router:Router){}

  getBalance(){
    this.router.navigate(['address-balance/',this.address]);
  }
}
