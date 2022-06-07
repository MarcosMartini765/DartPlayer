package com.martini.dartplayer.presentation.components.toDelete

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.dartplayer.R
import com.martini.dartplayer.domain.entity.relations.AlbumWithSongs
import com.martini.dartplayer.domain.entity.relations.ArtistWithAlbums
import com.martini.dartplayer.domain.entity.relations.PlaylistWithSongs
import com.martini.dartplayer.domain.entity.song.Song
import com.martini.dartplayer.presentation.components.SelectedMusicViewModel
import com.martini.dartplayer.presentation.components.dashboard.album.AlbumArt
import com.martini.dartplayer.presentation.components.dashboard.song.SongArt

@ExperimentalMaterialApi
@Composable
fun ToDeleteContent(
    selected: SelectedMusicViewModel = hiltViewModel(),
) {
    LazyColumn {
        items(
            items = selected.state.value.music,
            key = {
                when (it) {
                    is Song -> {
                        it.id
                    }
                    is AlbumWithSongs -> {
                        it.album.albumName
                    }
                    is ArtistWithAlbums -> {
                        it.artist.artistName
                    }
                    is PlaylistWithSongs -> {
                        it.playlist.playlistName
                    }
                    else -> {}
                }
            }
        ) { music ->
            when (music) {
                is Song -> {
                    ListItem(
                        icon = { SongArt(music) },
                        text = { Text(music.name, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                        secondaryText = {
                            Text(
                                music.album,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        trailing = {
                            IconButton(onClick = { selected.addOrRemoveSong(music) }) {
                                Icon(
                                    Icons.Filled.Remove,
                                    contentDescription = stringResource(R.string.RemoveSong)
                                )
                            }
                        }
                    )
                }
                is AlbumWithSongs -> {
                    ListItem(
                        icon = { AlbumArt(music) },
                        text = {
                            Text(
                                music.album.albumName,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        secondaryText = {
                            Text(
                                "${music.songs.count()} ${stringResource(R.string.song)}(s)",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        trailing = {
                            IconButton(onClick = { selected.addOrRemoveAlbum(music) }) {
                                Icon(
                                    Icons.Filled.Remove,
                                    contentDescription = stringResource(R.string.RemoveAlbum)
                                )
                            }
                        }
                    )
                }
                is ArtistWithAlbums -> {
                    ListItem(
                        icon = {
                            Icon(
                                Icons.Filled.ArtTrack,
                                contentDescription = stringResource(R.string.artist)
                            )
                        },
                        text = {
                            Text(
                                music.artist.artistName,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        secondaryText = {
                            Text(
                                "${music.albums.count()} ${stringResource(R.string.album)}(s)",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        trailing = {
                            IconButton(onClick = { selected.addOrRemoveArtist(music) }) {
                                Icon(
                                    Icons.Filled.Remove,
                                    contentDescription = stringResource(R.string.RemoveArtist)
                                )
                            }
                        }
                    )
                }
                is PlaylistWithSongs -> {
                    ListItem(
                        icon = {
                            Icon(
                                Icons.Filled.ArtTrack,
                                contentDescription = stringResource(R.string.artist)
                            )
                        },
                        text = {
                            Text(
                                music.playlist.playlistName,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        secondaryText = {
                            Text(
                                "${music.songs.count()} ${stringResource(R.string.song)}(s)",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        trailing = {
                            IconButton(onClick = { selected.addOrRemovePlaylist(music) }) {
                                Icon(
                                    Icons.Filled.Remove,
                                    contentDescription = stringResource(R.string.RemoveArtist)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}