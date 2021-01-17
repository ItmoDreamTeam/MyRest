//
//  PhotosCollectionView.swift
//  ios
//
//  Created by Артем Розанов on 14.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared

final class PhotosCollectionView: UICollectionView, ConfigurableView {
  typealias Model = RestaurantInfo

  private var viewModel: RestaurantInfo?

  init() {
    let layout = UICollectionViewFlowLayout()
    layout.scrollDirection = .horizontal
    super.init(frame: .zero, collectionViewLayout: layout)
    backgroundColor = .white
    showsVerticalScrollIndicator = false
    showsHorizontalScrollIndicator = false
    delegate = self
    dataSource = self
    register(PhotoCell.self, forCellWithReuseIdentifier: PhotoCell.reuseId)
    translatesAutoresizingMaskIntoConstraints = false
    layout.minimumLineSpacing = PhotosCollectionViewConstants.minimumLineSpacing
    layout.sectionInset = PhotosCollectionViewConstants.sectionInsets
  }

  required init?(coder: NSCoder) {
    super.init(coder: coder)
    backgroundColor = .white
    if let layout = collectionViewLayout as? UICollectionViewFlowLayout {
      layout.scrollDirection = .horizontal
      layout.minimumLineSpacing = PhotosCollectionViewConstants.minimumLineSpacing
      layout.sectionInset = PhotosCollectionViewConstants.sectionInsets
    }
    showsVerticalScrollIndicator = false
    showsHorizontalScrollIndicator = false
    delegate = self
    dataSource = self
    register(PhotoCell.self, forCellWithReuseIdentifier: PhotoCell.reuseId)
    translatesAutoresizingMaskIntoConstraints = false
  }

  func configure(with model: RestaurantInfo) {
    self.viewModel = model
  }
}

extension PhotosCollectionView: UICollectionViewDataSource {
  func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    return viewModel?.photos.count ?? 0
  }

  func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
    guard
      let cell = dequeueReusableCell(
        withReuseIdentifier: PhotoCell.reuseId,
        for: indexPath
        ) as? PhotoCell,
      let photos = viewModel?.photos
    else { return UICollectionViewCell() }
    cell.configure(with: photos[indexPath.row])
    return cell
  }
}

extension PhotosCollectionView: UICollectionViewDelegateFlowLayout {
  func collectionView(
    _ collectionView: UICollectionView,
    layout collectionViewLayout: UICollectionViewLayout,
    sizeForItemAt indexPath: IndexPath
  ) -> CGSize {
    return CGSize(width: PhotosCollectionViewConstants.cellWidth, height: frame.height * 0.8)
  }
}

extension PhotosCollectionView: UICollectionViewDelegate {}
