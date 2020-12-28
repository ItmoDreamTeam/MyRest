//
//  RestaurantListViewController.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

protocol RestaurantListView: UIViewController {
  func onRestaurantsFetchCompleted(_ restaurants: [RestaurantViewModel])
  func onRestaurantsFetchError(_ error: Error)
}

final class RestaurantListViewController: UIViewController, RestaurantListView {

  private var searchView: SearchView
  private var collectionView: VerticalCollectionView

  var router: RestaurantListRouter?
  var interactor: RestaurantListInteractor?

  init() {
    searchView = SearchView()
    collectionView = VerticalCollectionView()
    super.init(nibName: nil, bundle: nil)
  }

  required init?(coder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
    interactor?.restaurantListDidRequestRestaurants(self, byKeyword: "")
    configureNavBar()
    configureSearchTextField()
    configureCollectionView()
  }

  private func configureNavBar() {
    navigationItem.title = "MyRest"
    navigationItem.largeTitleDisplayMode = .always
    navigationController?.navigationBar.prefersLargeTitles = true

    navigationItem.rightBarButtonItem = UIBarButtonItem(
      image: UIImage(named: "info"),
      style: .plain,
      target: self,
      action: #selector(goToAboutScene)
    )
    navigationItem.rightBarButtonItem?.tintColor = .black
  }

  private func configureSearchTextField() {
    view.addSubview(searchView)
    searchView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor).isActive = true
    searchView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    searchView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
  }

  private func configureCollectionView() {
    view.addSubview(collectionView)
    collectionView.topAnchor.constraint(equalTo: searchView.bottomAnchor).isActive = true
    collectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
    collectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
    collectionView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
  }

  @objc private func goToAboutScene() {
    router?.restaurantListShouldOpenAboutScene(self)
  }

  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
  }

  // MARK: - RestaurantListView methods

  func onRestaurantsFetchCompleted(_ restaurants: [RestaurantViewModel]) {
    collectionView.configure(with: restaurants)
    collectionView.fotterView?.activityIndicatorView.stopAnimating()
    DispatchQueue.main.async {
      self.collectionView.reloadData()
    }
  }

  func onRestaurantsFetchError(_ error: Error) {
    fatalError("Not implemented yet")
  }
}
