import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderMain } from '../order-main.model';

@Component({
  selector: 'jhi-order-main-detail',
  templateUrl: './order-main-detail.component.html',
})
export class OrderMainDetailComponent implements OnInit {
  orderMain: IOrderMain | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderMain }) => {
      this.orderMain = orderMain;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
