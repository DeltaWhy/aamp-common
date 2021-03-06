package net.miscjunk.aamp.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.Gson;

public class HttpPlayerHandler extends AbstractHandler {
    private PlayerClient player;
    private Gson gson;

    public HttpPlayerHandler(PlayerClient appHandler, Gson gson) {
        this.player = appHandler;
        this.gson = gson;
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        baseRequest.setHandled(true);
        String[] path = target.split("/");
        if ("GET".equals(request.getMethod())) {
            System.out.println("GET " + target);
            if (path[1].equals("songs")) {
                if (path.length < 3 || path[2].equals("")) {
                    Playlist allSongs = player.getAllSongs();
                    String json = gson.toJson(allSongs.getSongs());//don't recursively die
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
            } else if (path[1].equals("playlists")) {
                if (path.length < 3 || path[2].equals("")) {
                    String json = gson.toJson(player.getPlaylists());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
            } else if (path[1].equals("queue")) {
                if (path.length < 3 || path[2].equals("")) {
                    String json = gson.toJson(player.getQueue());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
            } else if (path[1].equals("providers")) {
                if (path.length < 3 || path[2].equals("")) {
                    String json = gson.toJson(player.getProviders());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
            }
        } else if ("POST".equals(request.getMethod())) {
            System.out.println("POST " + target);
            if (path[1].equals("control")) {
                if (path.length < 3 || path[2].equals("")) {
                    BufferedReader bodyReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                    String action = bodyReader.readLine();
                    bodyReader.close();
                    System.out.println("With action " + action);
                    boolean success = false;
                    if("play".equals(action)) {
                    	success = player.play();
                    } else if("pause".equals(action)) {
                    	success = player.pause();
                    } else if(action.startsWith("volume=")) {
                    	success = player.setVolume(Double.parseDouble(action.replace("volume=", "")));
                    } else if(action.startsWith("seek=")) {
                    	success = player.seek(Double.parseDouble(action.replace("seek=", "")));
                    }else if("next".equals(action)) {
                    	success = player.next();
                	}else if ("prev".equals(action)) {
                		success = player.prev();
        			}else if(action.startsWith("skipTo=")) {
        				String id = action.replace("skipTo=", "");
        				success = player.skipTo(id);
        			}
                    if(success) {
                    	response.getWriter().println("SET:" + action);
                    }else {response.getWriter().println("FAILED: SET:" + action);}
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } else if (path[1].equals("query")) {
                if (path.length < 3 || path[2].equals("")) {
                    InputStreamReader bodyReader = new InputStreamReader(request.getInputStream());
                    Query query = gson.fromJson(bodyReader, Query.class);
                    System.out.println(gson.toJson(query));
                    Playlist result = player.buildPlaylist(query);
                    
                    String json = gson.toJson(result);
                    System.out.println(json);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
            } else if (path[1].equals("playlists")) {
                if (path.length < 3 || path[2].equals("")) {
                    InputStreamReader bodyReader = new InputStreamReader(request.getInputStream());
                    Playlist p = gson.fromJson(bodyReader, Playlist.class);
                    if (player.addPlaylist(p)) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print("Added playlist.");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().print("Couldn't add playlist.");
                    }
                }
            } else if (path[1].equals("providers")) {
                if (path.length < 3 || path[2].equals("")) {
                    InputStreamReader bodyReader = new InputStreamReader(request.getInputStream());
                    MusicProvider p = gson.fromJson(bodyReader, MusicProvider.class);
                    if (p == null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().print("Couldn't instantiate provider.");
                    } else if (player.addProvider(p)) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print("Added provider.");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().print("Couldn't add provider.");
                    }
                }
            } else if (path[1].equals("shuffle")) {
                if (path.length < 3 || path[2].equals("")) {
                    player.shuffleQueue();
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print("Shuffled queue.");
                }
            }
        } else if ("PUT".equals(request.getMethod())) {
            System.out.println("PUT " + target);
            if (path[1].equals("queue")) {
                if (path.length < 3 || path[2].equals("")) {
                    InputStreamReader bodyReader = new InputStreamReader(request.getInputStream());
                    MusicQueue queue = new MusicQueue(gson.fromJson(bodyReader, Playlist.class));
                    
                    player.setQueue(queue);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print("Updated queue.");
                }
            } else if (path[1].equals("playlists")) {
                if (path.length >= 3 && (path.length < 4 || path[3].equals(""))) {
                    String playlistId = path[2];
                    if (player.getPlaylist(playlistId) == null) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getOutputStream().print("No such playlist.");
                        return;
                    }
                    InputStreamReader bodyReader = new InputStreamReader(request.getInputStream());
                    Playlist p = gson.fromJson(bodyReader, Playlist.class);
                    if (player.updatePlaylist(p)) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print("Updated playlist.");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().print("Couldn't update playlist.");
                    }
                }
            } else if (path[1].equals("providers")) {
                if (path.length >= 3 && (path.length < 4 || path[3].equals(""))) {
                    String providerId = path[2];
                    if (player.getProvider(providerId) == null) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getOutputStream().print("No such provider.");
                        return;
                    }
                    InputStreamReader bodyReader = new InputStreamReader(request.getInputStream());
                    MusicProvider p = gson.fromJson(bodyReader, MusicProvider.class);
                    if (p == null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().print("Couldn't instantiate provider.");
                    } else if (player.updateProvider(p)) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print("Updated provider.");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().print("Couldn't update provider.");
                    }
                }
            }
        } else if ("DELETE".equals(request.getMethod())) {
            System.out.println("DELETE " + target);
            if (path[1].equals("playlists")) {
                if (path.length >= 3 && (path.length < 4 || path[3].equals(""))) {
                    String playlistId = path[2];
                    if (player.getPlaylist(playlistId) == null) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getOutputStream().print("No such playlist.");
                    } else {
                        player.removePlaylist(playlistId);
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print("Deleted playlist.");
                    }
                }
            } else if (path[1].equals("providers")) {
                if (path.length >= 3 && (path.length < 4 || path[3].equals(""))) {
                    String providerId = path[2];
                    if (player.getProvider(providerId) == null) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getOutputStream().print("No such provider.");
                    } else {
                        player.removeProvider(providerId);
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print("Deleted provider.");
                    }
                }
            }
        }else {
        	response.setStatus(404);
        }
    }
}
