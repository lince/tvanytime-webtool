/**
 * Copyright 2003 British Broadcasting Corporation
 *
 * This file is part of the BBC R&D TV-Anytime Java API.
 *
 * The BBC R&D TV-Anytime Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * The BBC R&D TV-Anytime Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the BBC R&D TV-Anytime Java API; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */


package bbc.rd.tvanytime.util;

import java.util.*;

/**
 * TVAnytimeGenreToolBox: Implementation of GenreToolbox specifically for TV-Anytime Genres.
 *
 * @author Chris Akanbi, BBC Research & Development, April 2002
 * @version 1.0
 * 
 * Modified 13/2/04 T.Ferne: Reversed order of elements obtained from getSubGenre() methods.
 * Modified 29/4/04 T.Ferne: Updated so compatible with ETSI TS 102 822-3-1 V1.1.1 (2003-10)
 */

public class TVAnytimeGenreToolbox extends GenreToolbox
{
	private static Hashtable table;

	//called when initializing class
	static
	{
		//table to store TVAnytime genres
		table = new Hashtable(800);

	    // Intention table
		table.put("1", "Intention");
		table.put("1.1", "Intention.Entertainment");
		table.put("1.1.1", "Intention.Entertainment.Pure entertainment");
		table.put("1.1.2", "Intention.Entertainment.Informative entertainment");
		table.put("1.2", "Intention.Information");
		table.put("1.2.1", "Intention.Information.Government");
		table.put("1.2.2", "Intention.Information.Pure information");
		table.put("1.2.3", "Intention.Information.Infotainment");
		table.put("1.2.4", "Intention.Information.Advice");
		table.put("1.3", "Intention.Education");
		table.put("1.3.1", "Intention.Education.Schools Programmes");
		table.put("1.3.1.1", "Intention.Education.Schools Programmes.Primary");
		table.put("1.3.1.2", "Intention.Education.Schools Programmes.Secondary");
		table.put("1.3.1.3", "Intention.Education.Schools Programmes.Tertiary");
		table.put("1.3.2", "Intention.Education.Lifelong/Further education");
		table.put("1.4", "Intention.Promotional");
 		table.put("1.5", "Intention.Advertising");
 		table.put("1.6", "Intention.Retail");
 		table.put("1.7", "Intention.Fund-raising");
 		table.put("1.8", "Intention.Enrichment");
 		table.put("1.8.1", "Intention.Enrichment.General enrichment");
 		table.put("1.8.2", "Intention.Enrichment.Inspirational enrichment");

	    // Format table
		table.put("2", "Format");
		table.put("2.1", "Format.Structured");
		table.put("2.1.1", "Format.Structured.Bulletin");
		table.put("2.1.2", "Format.Structured.Magazine");
		table.put("2.1.3", "Format.Structured.Commented event");
		table.put("2.1.4", "Format.Structured.Documentary");
		table.put("2.1.5", "Format.Structured.Discussion/Interview/Debate");
		table.put("2.1.6", "Format.Structured.Lecture/Speech/Presentation");
		table.put("2.1.7", "Format.Structured.Textual (incl, relayed teletext)");
		table.put("2.1.8", "Format.Structured.Phone-in");
		table.put("2.1.9", "Format.Structured.DJ with discs");
		table.put("2.2", "Format.Representation/Play");
		table.put("2.2.1", "Format.Representation/Play.Fictional portrayal of life");
	    table.put("2.2.2", "Format.Representation/Play.Readings");
	    table.put("2.2.3", "Format.Representation/Play.Representation with puppets");
		table.put("2.3", "Format.Cartoon/Animation");
		table.put("2.3.1", "Format.Cartoon/Animation.Anime");
	    table.put("2.3.2", "Format.Cartoon/Animation.Computer");
		table.put("2.3.3", "Format.Cartoon/Animation.Cartoon");
		table.put("2.4", "Format.Show");
		table.put("2.4.1", "Format.Show.Hosted show");
		table.put("2.4.1.1", "Format.Show.Hosted show.Simple game show");
		table.put("2.4.1.2", "Format.Show.Hosted show.Big game show");
 		table.put("2.4.2", "Format.Show.Panel-show");
		table.put("2.4.2.1", "Format.Show.Panel-show.Simple game show");
		table.put("2.4.2.2", "Format.Show.Panel-show.Big game show");
		table.put("2.4.3", "Format.Show.Non-hosted show");
		table.put("2.4.4", "Format.Show.Standup comedian(s)");
		table.put("2.5", "Format.Artistic performance");
		table.put("2.7", "Format.Interactive");
		table.put("2.7.1", "Format.Interactive.Local Interactivity");
		table.put("2.7.1.1", "Format.Interactive.Local Interactivity.Static informational");
		table.put("2.7.1.2", "Format.Interactive.Local Interactivity.Dynamic informational");
		table.put("2.7.1.3", "Format.Interactive.Local Interactivity.Viewing chats");
		table.put("2.7.1.4", "Format.Interactive.Local Interactivity.Quiz - basic multiple choice");
		table.put("2.7.1.5", "Format.Interactive.Local Interactivity.Quiz - text or number entry answers");
		table.put("2.7.1.6", "Format.Interactive.Local Interactivity.Re-ordering");
		table.put("2.7.1.7", "Format.Interactive.Local Interactivity.Positional");
		table.put("2.7.1.8", "Format.Interactive.Local Interactivity.Sync quiz");
		table.put("2.7.1.9", "Format.Interactive.Local Interactivity.Timer quiz");
		table.put("2.7.1.10", "Format.Interactive.Local Interactivity.Elimination and timer");
		table.put("2.7.1.11", "Format.Interactive.Local Interactivity.Categories");
		table.put("2.7.1.12", "Format.Interactive.Local Interactivity.Level based quiz/game");
		table.put("2.7.1.13", "Format.Interactive.Local Interactivity.Following a sequence");
		table.put("2.7.1.14", "Format.Interactive.Local Interactivity.Local multi player");
		table.put("2.7.1.15", "Format.Interactive.Local Interactivity.Multi stream audio-video");
		table.put("2.7.1.16", "Format.Interactive.Local Interactivity.Enhanced advertisement");
		table.put("2.7.1.17", "Format.Interactive.Local Interactivity.Logic based games");
		table.put("2.7.1.18", "Format.Interactive.Local Interactivity.Word games");
		table.put("2.7.1.19", "Format.Interactive.Local Interactivity.Positional games");
		table.put("2.7.1.20", "Format.Interactive.Local Interactivity.Board games");
		table.put("2.7.1.21", "Format.Interactive.Local Interactivity.Text based gaming");
		table.put("2.7.1.22", "Format.Interactive.Local Interactivity.Dynamic 2D/3D graphics");
		table.put("2.7.2", "Format.Interactive.Intermittent Response");
		table.put("2.7.2.1", "Format.Interactive.Intermittent Response.Single impulse vote");
		table.put("2.7.2.2", "Format.Interactive.Intermittent Response.Impulse vote from choices");
		table.put("2.7.2.3", "Format.Interactive.Intermittent Response.Impulse Yes/No vote");
		table.put("2.7.2.4", "Format.Interactive.Intermittent Response.Impulse vote with a value");
		table.put("2.7.2.5", "Format.Interactive.Intermittent Response.Submit answers/form");
		table.put("2.7.2.6", "Format.Interactive.Intermittent Response.SMS using mobile");
		table.put("2.7.2.7", "Format.Interactive.Intermittent Response.SMS using TV remote");
		table.put("2.7.2.8", "Format.Interactive.Intermittent Response.Impulse gambling");
		table.put("2.7.2.9", "Format.Interactive.Intermittent Response.Impulse transaction");
		table.put("2.7.2.10", "Format.Interactive.Intermittent Response.Multi player TS networked services/games");
		table.put("2.7.2.11", "Format.Interactive.Intermittent Response.Interactive advertisement");
		table.put("2.7.3", "Format.Interactive.Always on connection");
		table.put("2.7.3.1", "Format.Interactive.Always on connection.Chat Forum");
		table.put("2.7.3.2", "Format.Interactive.Always on connection.Chat Forum via web");
		table.put("2.7.3.3", "Format.Interactive.Always on connection.Threaded mail discussions");
		table.put("2.7.3.4", "Format.Interactive.Always on connection.Point to point");
		table.put("2.7.3.5", "Format.Interactive.Always on connection.3rd party point to point");
		table.put("2.7.3.6", "Format.Interactive.Always on connection.Voice chat using mic capability");
		table.put("2.7.3.7", "Format.Interactive.Always on connection.Dual player networked services/games");
		table.put("2.7.3.8", "Format.Interactive.Always on connection.Multi player RT networked services/games");
		table.put("2.7.3.9", "Format.Interactive.Always on connection.Gambling services");
		table.put("2.7.3.10", "Format.Interactive.Always on connection.Impulse transaction");
		table.put("2.7.3.11", "Format.Interactive.Always on connection.Non-linear audio-video");

		// Content table
		table.put("3", "Content");
		table.put("3.1", "Content.Non-Fiction");
		table.put("3.1.1", "Content.Non-Fiction.News");
		table.put("3.1.1.1", "Content.Non-Fiction.News.Daily news");
		table.put("3.1.1.2", "Content.Non-Fiction.News.Special news/edition");
		table.put("3.1.1.3", "Content.Non-Fiction.News.Special Reports");
		table.put("3.1.1.4", "Content.Non-Fiction.News.Commentary");
		table.put("3.1.1.5", "Content.Non-Fiction.News.Periodical/General");
		table.put("3.1.1.6", "Content.Non-Fiction.News.National politics/National Assembly");
		table.put("3.1.1.7", "Content.Non-Fiction.News.Economy/Market/Financial/Business");
		table.put("3.1.1.8", "Content.Non-Fiction.News.Foreign/International");
		table.put("3.1.1.9", "Content.Non-Fiction.News.Sports");
		table.put("3.1.1.10", "Content.Non-Fiction.News.Cultural");
		table.put("3.1.1.11", "Content.Non-Fiction.News.Local/regional");
		table.put("3.1.1.12", "Content.Non-Fiction.News.Traffic");
		table.put("3.1.1.13", "Content.Non-Fiction.News.Weather forecasts");
		table.put("3.1.1.14", "Content.Non-Fiction.News.Service information");
		table.put("3.1.1.15", "Content.Non-Fiction.News.Public Affairs");
		table.put("3.1.1.16", "Content.Non-Fiction.News.Current affairs");
		table.put("3.1.2", "Content.Non-Fiction.Philosophies of life");
		table.put("3.1.2.1", "Content.Non-Fiction.Philosophies of life.Religion");
		table.put("3.1.2.1.1", "Content.Non-Fiction.Philosophies of life.Religion.Buddhism");
		table.put("3.1.2.1.2", "Content.Non-Fiction.Philosophies of life.Religion.Hinduism");
		table.put("3.1.2.1.3", "Content.Non-Fiction.Philosophies of life.Religion.Christianity");
		table.put("3.1.2.1.4", "Content.Non-Fiction.Philosophies of life.Religion.Islam");
		table.put("3.1.2.1.5", "Content.Non-Fiction.Philosophies of life.Religion.Judaism");
		table.put("3.1.2.1.6", "Content.Non-Fiction.Philosophies of life.Religion.Atheism");
		table.put("3.1.2.1.7", "Content.Non-Fiction.Philosophies of life.Religion.Agnosticism");
		table.put("3.1.2.1.8", "Content.Non-Fiction.Philosophies of life.Religion.Shintoism");
		table.put("3.1.2.2", "Content.Non-Fiction.Philosophies of life.Non-religious philosophies");
		table.put("3.1.2.2.1", "Content.Non-Fiction.Philosophies of life.Non-religious philosophies.Communism");
		table.put("3.1.2.2.2", "Content.Non-Fiction.Philosophies of life.Non-religious philosophies.Humanism");
		table.put("3.1.2.2.3", "Content.Non-Fiction.Philosophies of life.Non-religious philosophies.Capitalism");
		table.put("3.1.2.2.4", "Content.Non-Fiction.Philosophies of life.Non-religious philosophies.Socialism");
		table.put("3.1.2.2.5", "Content.Non-Fiction.Philosophies of life.Non-religious philosophies.Libertarianism");
		table.put("3.1.2.2.6", "Content.Non-Fiction.Philosophies of life.Non-religious philosophies.Republicanism");
		table.put("3.1.3", "Content.Non-Fiction.General non-fiction");
		table.put("3.1.3.1", "Content.Non-Fiction.General non-fiction.Political");
		table.put("3.1.3.2", "Content.Non-Fiction.General non-fiction.Social");
		table.put("3.1.3.3", "Content.Non-Fiction.General non-fiction.Economic");
		table.put("3.1.3.4", "Content.Non-Fiction.General non-fiction.Legal");
		table.put("3.1.3.5", "Content.Non-Fiction.General non-fiction.Finance");
		table.put("3.1.3.6", "Content.Non-Fiction.General non-fiction.Education");
		table.put("3.1.3.7", "Content.Non-Fiction.General non-fiction.International affairs");
		table.put("3.1.3.8", "Content.Non-Fiction.General non-fiction.Military/Defence");
		table.put("3.1.4", "Content.Non-Fiction.Arts & Media");
		table.put("3.1.4.1", "Content.Non-Fiction.Arts & Media.Music");
		table.put("3.1.4.2", "Content.Non-Fiction.Arts & Media.Dance");
		table.put("3.1.4.3", "Content.Non-Fiction.Arts & Media.Theatre");
		table.put("3.1.4.4", "Content.Non-Fiction.Arts & Media.Opera");
		table.put("3.1.4.5", "Content.Non-Fiction.Arts & Media.Cinema");
		table.put("3.1.4.6", "Content.Non-Fiction.Arts & Media.Advertising");
		table.put("3.1.4.7", "Content.Non-Fiction.Arts & Media.Press");
		table.put("3.1.4.8", "Content.Non-Fiction.Arts & Media.Plastic Arts");
		table.put("3.1.4.9", "Content.Non-Fiction.Arts & Media.Fine arts");
		table.put("3.1.4.10", "Content.Non-Fiction.Arts & Media.Experimental arts");
		table.put("3.1.4.11", "Content.Non-Fiction.Arts & Media.Architecture");
		table.put("3.1.4.12", "Content.Non-Fiction.Arts & Media.Showbiz");
		table.put("3.1.4.13", "Content.Non-Fiction.Arts & Media.Television");
		table.put("3.1.4.14", "Content.Non-Fiction.Arts & Media.Radio");
		table.put("3.1.4.15", "Content.Non-Fiction.Arts & Media.New media");
		table.put("3.1.5", "Content.Non-Fiction.Humanities");
		table.put("3.1.5.1", "Content.Non-Fiction.Humanities.Literature");
		table.put("3.1.5.2", "Content.Non-Fiction.Humanities.Languages");
		table.put("3.1.5.3", "Content.Non-Fiction.Humanities.History");
		table.put("3.1.5.4", "Content.Non-Fiction.Humanities.Culture/tradition/anthropology/Ethnic studies");
		table.put("3.1.5.5", "Content.Non-Fiction.Humanities.War/Conflict");
		table.put("3.1.6", "Content.Non-Fiction.Sciences");
		table.put("3.1.6.1", "Content.Non-Fiction.Sciences.Applied sciences");
		table.put("3.1.6.2", "Content.Non-Fiction.Sciences.Nature/natural sciences");
		table.put("3.1.6.3", "Content.Non-Fiction.Sciences.Animals/Wildlife");
		table.put("3.1.6.4", "Content.Non-Fiction.Sciences.Environment/geography");
		table.put("3.1.6.5", "Content.Non-Fiction.Sciences.Space/Universe");
		table.put("3.1.6.6", "Content.Non-Fiction.Sciences.Physical sciences");
		table.put("3.1.6.7", "Content.Non-Fiction.Sciences.Medecine");
		table.put("3.1.6.8", "Content.Non-Fiction.Sciences.Technology");
		table.put("3.1.6.9", "Content.Non-Fiction.Sciences.Physiology");
		table.put("3.1.6.10", "Content.Non-Fiction.Sciences.Psychology");
		table.put("3.1.6.11", "Content.Non-Fiction.Sciences.Social");
		table.put("3.1.6.12", "Content.Non-Fiction.Sciences.Spiritual");
		table.put("3.1.6.13", "Content.Non-Fiction.Sciences.Mathematics");
		table.put("3.1.6.14", "Content.Non-Fiction.Sciences.Archaeology");
		table.put("3.1.7", "Content.Non-Fiction.Human interest");
		table.put("3.1.7.1", "Content.Non-Fiction.Human interest.Reality");
		table.put("3.1.7.2", "Content.Non-Fiction.Human interest.Society/Show business/Gossip");
		table.put("3.1.7.3", "Content.Non-Fiction.Human interest.Biography/notable personalities");
		table.put("3.1.7.4", "Content.Non-Fiction.Human interest.Personal problems");
		table.put("3.1.7.5", "Content.Non-Fiction.Human interest.Investigative journalism");
		table.put("3.1.7.6", "Content.Non-Fiction.Human interest.Museums");
		table.put("3.1.7.7", "Content.Non-Fiction.Human interest.Religious buildings");
	    table.put("3.1.8", "Content.Non-Fiction.Transport and Communications");
		table.put("3.1.8.1", "Content.Non-Fiction.Transport and Communications.Air");
		table.put("3.1.8.2", "Content.Non-Fiction.Transport and Communications.Land");
		table.put("3.1.8.3", "Content.Non-Fiction.Transport and Communications.Sea");
		table.put("3.1.8.4", "Content.Non-Fiction.Transport and Communications.Space");
	    table.put("3.1.9", "Content.Non-Fiction.Events");
		table.put("3.1.9.1", "Content.Non-Fiction.Events.Anniversary");
		table.put("3.1.9.2", "Content.Non-Fiction.Events.Fair");
		table.put("3.1.9.3", "Content.Non-Fiction.Events.Tradeshow");
		table.put("3.1.9.4", "Content.Non-Fiction.Events.Musical");
		table.put("3.1.9.5", "Content.Non-Fiction.Events.Exhibition");
		table.put("3.2", "Content.Sports");
		table.put("3.2.1", "Content.Sports.Athletics");
		table.put("3.2.1.1", "Content.Sports.Athletics.Field");
		table.put("3.2.1.2", "Content.Sports.Athletics.Track");
		table.put("3.2.1.3", "Content.Sports.Athletics.Combined athletics");
		table.put("3.2.1.4", "Content.Sports.Athletics.Running");
		table.put("3.2.1.5", "Content.Sports.Athletics.Cross-country");
		table.put("3.2.1.6", "Content.Sports.Athletics.Triathlon");
		table.put("3.2.2", "Content.Sports.Cycling/bicycle");
		table.put("3.2.2.1", "Content.Sports.Cycling/bicycle.Mountainbike");
		table.put("3.2.2.2", "Content.Sports.Cycling/bicycle.Bicross");
		table.put("3.2.2.3", "Content.Sports.Cycling/bicycle.Indoor cycling");
		table.put("3.2.2.4", "Content.Sports.Cycling/bicycle.Road Cycling");
		table.put("3.2.3", "Content.Sports.Team sports");
		table.put("3.2.3.1", "Content.Sports.Team sports.Football (American)");
		table.put("3.2.3.2", "Content.Sports.Team sports.Football (Australian)");
		table.put("3.2.3.3", "Content.Sports.Team sports.Football (Gaelic)");
		table.put("3.2.3.4", "Content.Sports.Team sports.Football (Indoor)");
		table.put("3.2.3.5", "Content.Sports.Team sports.Beach soccer");
		table.put("3.2.3.6", "Content.Sports.Team sports.Bandy");
		table.put("3.2.3.7", "Content.Sports.Team sports.Baseball");
		table.put("3.2.3.8", "Content.Sports.Team sports.Basketball");
		table.put("3.2.3.9", "Content.Sports.Team sports.Cricket");
		table.put("3.2.3.10", "Content.Sports.Team sports.Croquet");
		table.put("3.2.3.11", "Content.Sports.Team sports.Faustball");
		table.put("3.2.3.12", "Content.Sports.Team sports.Football (soccer)");
		table.put("3.2.3.13", "Content.Sports.Team sports.Handball");
		table.put("3.2.3.14", "Content.Sports.Team sports.Hockey");
		table.put("3.2.3.15", "Content.Sports.Team sports.Korfball");
		table.put("3.2.3.16", "Content.Sports.Team sports.Lacrosse");
		table.put("3.2.3.17", "Content.Sports.Team sports.Netball");
		table.put("3.2.3.18", "Content.Sports.Team sports.Roller skating");
		table.put("3.2.3.19", "Content.Sports.Team sports.Rugby");
		table.put("3.2.3.19.1", "Content.Sports.Team sports.Rugby.Rugby union");
		table.put("3.2.3.19.2", "Content.Sports.Team sports.Rugby.Rugby league");
		table.put("3.2.3.20", "Content.Sports.Team sports.Softball");
		table.put("3.2.3.21", "Content.Sports.Team sports.Volleyball");
		table.put("3.2.3.22", "Content.Sports.Team sports.Beach volley");
		table.put("3.2.3.23", "Content.Sports.Team sports.Hurling");
		table.put("3.2.4", "Content.Sports.Racket sports");
		table.put("3.2.4.1", "Content.Sports.Racket sports.Badminton");
		table.put("3.2.4.2", "Content.Sports.Racket sports.Racketball");
		table.put("3.2.4.3", "Content.Sports.Racket sports.Short tennis");
		table.put("3.2.4.4", "Content.Sports.Racket sports.Soft tennis");
		table.put("3.2.4.5", "Content.Sports.Racket sports.Squash");
		table.put("3.2.4.6", "Content.Sports.Racket sports.Table tennis");
		table.put("3.2.4.7", "Content.Sports.Racket sports.Tennis");
		table.put("3.2.5", "Content.Sports.Martial Arts");
		table.put("3.2.5.1", "Content.Sports.Martial Arts.Aikido");
		table.put("3.2.5.2", "Content.Sports.Martial Arts.Jai-alai");
		table.put("3.2.5.3", "Content.Sports.Martial Arts.Judo");
		table.put("3.2.5.4", "Content.Sports.Martial Arts.Ju-jitsu");
		table.put("3.2.5.5", "Content.Sports.Martial Arts.Karate");
		table.put("3.2.5.6", "Content.Sports.Martial Arts.Sumo/Fighting games");
		table.put("3.2.5.7", "Content.Sports.Martial Arts.Sambo");
		table.put("3.2.5.8", "Content.Sports.Martial Arts.Taekwondo");
		table.put("3.2.6", "Content.Sports.Water sports");
		table.put("3.2.6.1", "Content.Sports.Water sports.Bodyboarding");
		table.put("3.2.6.2", "Content.Sports.Water sports.Yatching");
		table.put("3.2.6.3", "Content.Sports.Water sports.Canoeing");
		table.put("3.2.6.4", "Content.Sports.Water sports.Diving");
		table.put("3.2.6.5", "Content.Sports.Water sports.Fishing");
		table.put("3.2.6.6", "Content.Sports.Water sports.Polo");
		table.put("3.2.6.7", "Content.Sports.Water sports.Rowing");
		table.put("3.2.6.8", "Content.Sports.Water sports.Sailing");
		table.put("3.2.6.9", "Content.Sports.Water sports.Sub-aquatics");
		table.put("3.2.6.10", "Content.Sports.Water sports.Surfing");
		table.put("3.2.6.11", "Content.Sports.Water sports.Swimming");
		table.put("3.2.6.12", "Content.Sports.Water sports.Water polo");
		table.put("3.2.6.13", "Content.Sports.Water sports.Water sking");
		table.put("3.2.6.14", "Content.Sports.Water sports.Windsurfing");
		table.put("3.2.7", "Content.Sports.Winter sports");
		table.put("3.2.7.1", "Content.Sports.Winter sports.Bobsleigh/tobogganing");
		table.put("3.2.7.2", "Content.Sports.Winter sports.Curling");
		table.put("3.2.7.3", "Content.Sports.Winter sports.Ice-hockey");
		table.put("3.2.7.4", "Content.Sports.Winter sports.Ice-skating");
		table.put("3.2.7.5", "Content.Sports.Winter sports.Luge");
		table.put("3.2.7.6", "Content.Sports.Winter sports.Skating");
		table.put("3.2.7.7", "Content.Sports.Winter sports.Skibob");
		table.put("3.2.7.8", "Content.Sports.Winter sports.Skiing");
		table.put("3.2.7.9", "Content.Sports.Winter sports.Sleddog");
		table.put("3.2.7.10", "Content.Sports.Winter sports.Snowboarding");
		table.put("3.2.7.11", "Content.Sports.Winter sports.Alpine skiing");
		table.put("3.2.7.12", "Content.Sports.Winter sports.Freestyle skiing");
		table.put("3.2.7.13", "Content.Sports.Winter sports.inline skating");
		table.put("3.2.7.14", "Content.Sports.Winter sports.Nordic skiing");
		table.put("3.2.7.15", "Content.Sports.Winter sports.Ski jumping");
		table.put("3.2.7.16", "Content.Sports.Winter sports.Speed skating");
		table.put("3.2.7.17", "Content.Sports.Winter sports.Figure skating");
		table.put("3.2.7.18", "Content.Sports.Winter sports.Ice-dance");
		table.put("3.2.7.19", "Content.Sports.Winter sports.Marathon");
		table.put("3.2.7.20", "Content.Sports.Winter sports.Short-track");
		table.put("3.2.7.21", "Content.Sports.Winter sports.Biathlon");
		table.put("3.2.8", "Content.Sports.Motor sports");
		table.put("3.2.8.1", "Content.Sports.Motor sports.Motor/auto racing");
		table.put("3.2.8.2", "Content.Sports.Motor sports.Motor boating/motor racing");
		table.put("3.2.8.3", "Content.Sports.Motor sports.Motor cycling");
		table.put("3.2.8.4", "Content.Sports.Motor sports.Formula 1");
		table.put("3.2.8.5", "Content.Sports.Motor sports.Indy car");
		table.put("3.2.8.6", "Content.Sports.Motor sports.Karting");
		table.put("3.2.8.7", "Content.Sports.Motor sports.Rally");
		table.put("3.2.8.8", "Content.Sports.Motor sports.Trucking");
		table.put("3.2.8.9", "Content.Sports.Motor sports.Tractor Pulling");
		table.put("3.2.8.10", "Content.Sports.Motor sports.Auto racing");
	    table.put("3.2.9", "Content.Sports.Social sports");
		table.put("3.2.9.1", "Content.Sports.Social sports.Billiards");
		table.put("3.2.9.2", "Content.Sports.Social sports.Boules");
		table.put("3.2.9.3", "Content.Sports.Social sports.Bowling");
		table.put("3.2.9.4", "Content.Sports.Social sports.Chess");
		table.put("3.2.9.5", "Content.Sports.Social sports.Dance sport");
		table.put("3.2.9.6", "Content.Sports.Social sports.Darts");
		table.put("3.2.9.7", "Content.Sports.Social sports.Pool");
		table.put("3.2.9.8", "Content.Sports.Social sports.Snooker");
		table.put("3.2.9.9", "Content.Sports.Social sports.Tug-of-war");
		table.put("3.2.9.10", "Content.Sports.Social sports.Balle Pelote");
		table.put("3.2.9.11", "Content.Sports.Social sports.Basque Pelote");
		table.put("3.2.9.12", "Content.Sports.Social sports.Trickshot");
		table.put("3.2.10", "Content.Sports.Gymnastics");
		table.put("3.2.10.1", "Content.Sports.Gymnastics.Assymetric bars");
		table.put("3.2.10.2", "Content.Sports.Gymnastics.Beam");
		table.put("3.2.10.3", "Content.Sports.Gymnastics.Horse");
		table.put("3.2.10.4", "Content.Sports.Gymnastics.Mat");
		table.put("3.2.10.5", "Content.Sports.Gymnastics.Parallel bars");
		table.put("3.2.10.6", "Content.Sports.Gymnastics.Rings");
		table.put("3.2.10.7", "Content.Sports.Gymnastics.Trampoline");
		table.put("3.2.11", "Content.Sports.Equestrian");
		table.put("3.2.11.1", "Content.Sports.Equestrian.Cart");
		table.put("3.2.11.2", "Content.Sports.Equestrian.Dressage");
		table.put("3.2.11.3", "Content.Sports.Equestrian.Horse racing");
		table.put("3.2.11.4", "Content.Sports.Equestrian.Polo");
		table.put("3.2.11.5", "Content.Sports.Equestrian.Jumping");
		table.put("3.2.11.6", "Content.Sports.Equestrian.Crossing");
		table.put("3.2.11.7", "Content.Sports.Equestrian.Trotting");
		table.put("3.2.12", "Content.Sports.Adventure sports");
		table.put("3.2.12.1", "Content.Sports.Adventure sports.Archery");
		table.put("3.2.12.2", "Content.Sports.Adventure sports.Extreme sports");
		table.put("3.2.12.3", "Content.Sports.Adventure sports.Mountaineering");
		table.put("3.2.12.4", "Content.Sports.Adventure sports.Climbing");
		table.put("3.2.12.5", "Content.Sports.Adventure sports.Orienteering");
		table.put("3.2.12.6", "Content.Sports.Adventure sports.Shooting");
		table.put("3.2.12.7", "Content.Sports.Adventure sports.Sport acrobatics");
		table.put("3.2.12.8", "Content.Sports.Adventure sports.Rafting");
		table.put("3.2.13", "Content.Sports.Strength-based sports");
		table.put("3.2.13.1", "Content.Sports.Strength-based sports.Body-building");
		table.put("3.2.13.2", "Content.Sports.Strength-based sports.Boxing");
		table.put("3.2.13.3", "Content.Sports.Strength-based sports.Combative sports");
		table.put("3.2.13.4", "Content.Sports.Strength-based sports.Power-lifting");
		table.put("3.2.13.5", "Content.Sports.Strength-based sports.Weight-lifting");
		table.put("3.2.13.6", "Content.Sports.Strength-based sports.Wrestling");
		table.put("3.2.14", "Content.Sports.Air sports");
		table.put("3.2.14.1", "Content.Sports.Air sports.Ballooning");
		table.put("3.2.14.2", "Content.Sports.Air sports.Hang gliding");
		table.put("3.2.14.3", "Content.Sports.Air sports.Sky diving");
		table.put("3.2.14.4", "Content.Sports.Air sports.Delta-plane");
		table.put("3.2.14.5", "Content.Sports.Air sports.Parachuting");
		table.put("3.2.14.6", "Content.Sports.Air sports.Kiting");
		table.put("3.2.14.7", "Content.Sports.Air sports.Aeronautics");
		table.put("3.2.14.8", "Content.Sports.Air sports.Gliding");
		table.put("3.2.14.9", "Content.Sports.Air sports.Flying");
		table.put("3.2.14.10", "Content.Sports.Air sports.Aerobatics");
		table.put("3.2.15", "Content.Sports.Golf");
		table.put("3.2.16", "Content.Sports.Fencing");
		table.put("3.2.17", "Content.Sports.Dog racing");
		table.put("3.2.18", "Content.Sports.Casting");
		table.put("3.2.19", "Content.Sports.Maccabi");
		table.put("3.2.20", "Content.Sports.Modern Pantathlon");
		table.put("3.2.21", "Content.Sports.Sombo");
		table.put("3.3", "Content.Leisure/Hobby");
		table.put("3.3.1", "Content.Leisure/Hobby.Do-it-yourself");
		table.put("3.3.2", "Content.Leisure/Hobby.Cookery");
		table.put("3.3.3", "Content.Leisure/Hobby.Gardening");
		table.put("3.3.4", "Content.Leisure/Hobby.Travel/Tourism");
		table.put("3.3.5", "Content.Leisure/Hobby.Adventure/Expeditions");
		table.put("3.3.6", "Content.Leisure/Hobby.Fishing");
		table.put("3.3.7", "Content.Leisure/Hobby.Outdoor ");
		table.put("3.3.8", "Content.Leisure/Hobby.Pet");
		table.put("3.3.9", "Content.Leisure/Hobby.Craft/Handicraft ");
		table.put("3.3.10", "Content.Leisure/Hobby.Art");
		table.put("3.3.11", "Content.Leisure/Hobby.Music");
		table.put("3.3.12", "Content.Leisure/Hobby.Board Games");
		table.put("3.3.13", "Content.Leisure/Hobby.Computer Games");
		table.put("3.3.14", "Content.Leisure/Hobby.Card Cames");
		table.put("3.3.15", "Content.Leisure/Hobby.Fitness/Keep-fit");
		table.put("3.3.16", "Content.Leisure/Hobby.Personal health");
		table.put("3.3.17", "Content.Leisure/Hobby.Car");
		table.put("3.3.18", "Content.Leisure/Hobby.Motorcycle/Motoring");
		table.put("3.3.19", "Content.Leisure/Hobby.Fashion");
		table.put("3.3.20", "Content.Leisure/Hobby.Life/House Keeping/Lifestyle ");
		table.put("3.3.21", "Content.Leisure/Hobby.Technology/Computing");
		table.put("3.3.22", "Content.Leisure/Hobby.Gaming");
		table.put("3.3.23", "Content.Leisure/Hobby.Shopping");
		table.put("3.3.24", "Content.Leisure/Hobby.Adult");
		table.put("3.3.25", "Content.Leisure/Hobby.Road safety");
		table.put("3.3.26", "Content.Leisure/Hobby.Consumer advice");
		table.put("3.3.27", "Content.Leisure/Hobby.Employment Advice");
		table.put("3.3.28", "Content.Leisure/Hobby.Boating");
		table.put("3.3.29", "Content.Leisure/Hobby.Parenting");
		table.put("3.3.30", "Content.Leisure/Hobby.Self-help");
		table.put("3.3.31", "Content.Leisure/Hobby.Collectibles");
		table.put("3.3.32", "Content.Leisure/Hobby.Jewelry");
		table.put("3.3.33", "Content.Leisure/Hobby.Beauty");
		table.put("3.3.34", "Content.Leisure/Hobby.Aviation");
		table.put("3.4", "Content.Fiction");
		table.put("3.4.1", "Content.Fiction.General light drama");
		table.put("3.4.2", "Content.Fiction.Soap");
		table.put("3.4.2.1", "Content.Fiction.Soap.Soap Opera");
		table.put("3.4.2.2", "Content.Fiction.Soap.Soap Special");
		table.put("3.4.2.3", "Content.Fiction.Soap.Soap Talk");
		table.put("3.4.3", "Content.Fiction.Romance");
		table.put("3.4.4", "Content.Fiction.Legal melodrama");
		table.put("3.4.5", "Content.Fiction.Medical melodrama");
		table.put("3.4.6", "Content.Fiction.Action");
		table.put("3.4.6.1", "Content.Fiction.Action.Adventure");
		table.put("3.4.6.2", "Content.Fiction.Action.Disaster");
		table.put("3.4.6.3", "Content.Fiction.Action.Mystery");
		table.put("3.4.6.4", "Content.Fiction.Action.Detective");
		table.put("3.4.6.5", "Content.Fiction.Action.Historical/Epic");
		table.put("3.4.6.6", "Content.Fiction.Action.Horror");
		table.put("3.4.6.7", "Content.Fiction.Action.Science fiction");
		table.put("3.4.6.8", "Content.Fiction.Action.War");
		table.put("3.4.6.9", "Content.Fiction.Action.Western");
		table.put("3.4.6.10", "Content.Fiction.Action.Thriller");
		table.put("3.4.6.11", "Content.Fiction.Action.Sports");
		table.put("3.4.6.12", "Content.Fiction.Action.Martial arts");
		table.put("3.4.6.13", "Content.Fiction.Action.Epic");
		table.put("3.4.7", "Content.Fiction.Fantasy/Fairy tale");
		table.put("3.4.8", "Content.Fiction.Erotica");
		table.put("3.4.9", "Content.Fiction.Drama based on real events (docudrama)");
		table.put("3.4.10", "Content.Fiction.Musical");
		table.put("3.4.11", "Content.Fiction.Comedy");
		table.put("3.4.12", "Content.Fiction.Effect movies");
		table.put("3.4.13", "Content.Fiction.Classical drama");
		table.put("3.4.14", "Content.Fiction.Period drama");
		table.put("3.4.15", "Content.Fiction.Contemporary drama");
		table.put("3.4.16", "Content.Fiction.Religious");
		table.put("3.4.17", "Content.Fiction.Poems/Stories");
		table.put("3.4.18", "Content.Fiction.Biography");
		table.put("3.4.19", "Content.Fiction.Psychological drama");
		table.put("3.5", "Content.Amusement");
		table.put("3.5.1", "Content.Amusement.Game show");
		table.put("3.5.2", "Content.Amusement.Quiz/Contest");
		table.put("3.5.3", "Content.Amusement.Variety show");
		table.put("3.5.4", "Content.Amusement.Surprise show");
		table.put("3.5.5", "Content.Amusement.Reality show");
		table.put("3.5.6", "Content.Amusement.Candid camera");
		table.put("3.5.7", "Content.Amusement.Comedy");
		table.put("3.5.7.1", "Content.Amusement.Comedy.Broken comedy");
		table.put("3.5.7.2", "Content.Amusement.Comedy.Romantic comedy");
		table.put("3.5.7.3", "Content.Amusement.Comedy.Sitcom");
		table.put("3.5.7.4", "Content.Amusement.Comedy.Satire");
		table.put("3.5.8", "Content.Amusement.Standup comedian(s)");
		table.put("3.5.9", "Content.Amusement.Humour");
		table.put("3.5.10", "Content.Amusement.Magic/Hypnotism");
		table.put("3.5.11", "Content.Amusement.Circus");
		table.put("3.5.12", "Content.Amusement.Dating show");
		table.put("3.5.13", "Content.Amusement.Bullfighting");
		table.put("3.5.14", "Content.Amusement.Rodeo");
		table.put("3.5.15", "Content.Amusement.Airshow");
	    table.put("3.6", "Content.Music and Dance");
		table.put("3.6.1", "Content.Music and Dance.Classical music");
		table.put("3.6.1.1", "Content.Music and Dance.Classical music.Early");
		table.put("3.6.1.2", "Content.Music and Dance.Classical music.Classical");
		table.put("3.6.1.3", "Content.Music and Dance.Classical music.Romantic");
		table.put("3.6.1.4", "Content.Music and Dance.Classical music.Contemporary");
		table.put("3.6.1.5", "Content.Music and Dance.Classical music.Light classical");
		table.put("3.6.1.6", "Content.Music and Dance.Classical music.Middle Ages");
		table.put("3.6.1.7", "Content.Music and Dance.Classical music.Renaissance");
		table.put("3.6.1.8", "Content.Music and Dance.Classical music.Baroque");
		table.put("3.6.1.9", "Content.Music and Dance.Classical music.Opera");
		table.put("3.6.1.10", "Content.Music and Dance.Classical music.Solo instruments");
		table.put("3.6.1.11", "Content.Music and Dance.Classical music.Chamber");
		table.put("3.6.1.12", "Content.Music and Dance.Classical music.Symphonic");
		table.put("3.6.1.13", "Content.Music and Dance.Classical music.Vocal");
		table.put("3.6.1.14", "Content.Music and Dance.Classical music.Choral");
		table.put("3.6.2", "Content.Music and Dance.Jazz");
		table.put("3.6.2.1", "Content.Music and Dance.Jazz.New Orleans/early jazz");
		table.put("3.6.2.2", "Content.Music and Dance.Jazz.Big band/Swing/Dixie");
		table.put("3.6.2.3", "Content.Music and Dance.Jazz.Blues/Soul jazz");
		table.put("3.6.2.4", "Content.Music and Dance.Jazz.Bop/Hard bop/Bebop");
		table.put("3.6.2.5", "Content.Music and Dance.Jazz.Traditional/Smooth");
		table.put("3.6.2.6", "Content.Music and Dance.Jazz.Cool/Free");
		table.put("3.6.2.7", "Content.Music and Dance.Jazz.Modern/Acid/Avant-garde");
		table.put("3.6.2.8", "Content.Music and Dance.Jazz.Latin & World jazz");
		table.put("3.6.2.9", "Content.Music and Dance.Jazz.Pop jazz/Jazz funk");
		table.put("3.6.2.10", "Content.Music and Dance.Jazz.Acid jazz/fusion");
		table.put("3.6.3", "Content.Music and Dance.Background music");
		table.put("3.6.3.1", "Content.Music and Dance.Background music.Middle-of-the-road");
		table.put("3.6.3.2", "Content.Music and Dance.Background music.Easy listening");
		table.put("3.6.3.3", "Content.Music and Dance.Background music.Ambient");
		table.put("3.6.3.4", "Content.Music and Dance.Background music.Mood music");
		table.put("3.6.3.5", "Content.Music and Dance.Background music.Oldies");
		table.put("3.6.3.6", "Content.Music and Dance.Background music.Love songs");
		table.put("3.6.3.7", "Content.Music and Dance.Background music.Dance hall");
		table.put("3.6.3.8", "Content.Music and Dance.Background music.Soundtrack");
		table.put("3.6.3.9", "Content.Music and Dance.Background music.Trailer");
		table.put("3.6.3.10", "Content.Music and Dance.Background music.Showtunes");
		table.put("3.6.3.11", "Content.Music and Dance.Background music.TV");
		table.put("3.6.3.12", "Content.Music and Dance.Background music.Cabaret");
		table.put("3.6.3.13", "Content.Music and Dance.Background music.Instrumental");
		table.put("3.6.3.14", "Content.Music and Dance.Background music.Sound clip");
		table.put("3.6.3.15", "Content.Music and Dance.Background music.Retro");
		table.put("3.6.4", "Content.Music and Dance.Pop-rock");
		table.put("3.6.4.1", "Content.Music and Dance.Pop-rock.Pop");
		table.put("3.6.4.2", "Content.Music and Dance.Pop-rock.Chanson/Ballad");
		table.put("3.6.4.3", "Content.Music and Dance.Pop-rock.Traditional rock and roll");
		table.put("3.6.4.4", "Content.Music and Dance.Pop-rock.Soft/Slow rock");
		table.put("3.6.4.5", "Content.Music and Dance.Pop-rock.Classic/dance/pop-rock");
		table.put("3.6.4.6", "Content.Music and Dance.Pop-rock.Folk");
		table.put("3.6.4.7", "Content.Music and Dance.Pop-rock.Punk/Funk rock");
		table.put("3.6.4.8", "Content.Music and Dance.Pop-rock.New Age");
		table.put("3.6.4.9", "Content.Music and Dance.Pop-rock.Instrumental/Band/Symphonic rock/Jam bands");
		table.put("3.6.4.10", "Content.Music and Dance.Pop-rock.Progressive/Alternative/Indie/Experimental/Art-rock");
		table.put("3.6.4.11", "Content.Music and Dance.Pop-rock.Seasonal/Holiday");
		table.put("3.6.4.12", "Content.Music and Dance.Pop-rock.Japanese pop-rock");
		table.put("3.6.4.13", "Content.Music and Dance.Pop-rock.Karaoke/Singing contests");
		table.put("3.6.5", "Content.Music and Dance.Blues/Rhythm and Blues/Soul/Gospel");
		table.put("3.6.5.1", "Content.Music and Dance.Blues/Rhythm and Blues/Soul/Gospel.Blues");
		table.put("3.6.5.2", "Content.Music and Dance.Blues/Rhythm and Blues/Soul/Gospel.R&B");
		table.put("3.6.5.3", "Content.Music and Dance.Blues/Rhythm and Blues/Soul/Gospel.Soul");
		table.put("3.6.5.4", "Content.Music and Dance.Blues/Rhythm and Blues/Soul/Gospel.Gospel");
		table.put("3.6.6", "Content.Music and Dance.Country and Western");
		table.put("3.6.7", "Content.Music and Dance.Rap/Hip Hop/Reggae");
		table.put("3.6.7.1", "Content.Music and Dance.Rap/Hip Hop/Reggae.Rap/Christian rap");
		table.put("3.6.7.2", "Content.Music and Dance.Rap/Hip Hop/Reggae.Hip Hop/Trip-Hop");
		table.put("3.6.7.3", "Content.Music and Dance.Rap/Hip Hop/Reggae.Reggae");
		table.put("3.6.7.4", "Content.Music and Dance.Rap/Hip Hop/Reggae.Ska/Gangsta");
		table.put("3.6.8", "Content.Music and Dance.Electronic/Club/Urban/Dance");
		table.put("3.6.8.1", "Content.Music and Dance.Electronic/Club/Urban/Dance.Acid/Punk/Acid Punk");
		table.put("3.6.8.2", "Content.Music and Dance.Electronic/Club/Urban/Dance.Disco");
		table.put("3.6.8.3", "Content.Music and Dance.Electronic/Club/Urban/Dance.Techno/Euro-Techno/Techno-Industrial/Industrial");
		table.put("3.6.8.4", "Content.Music and Dance.Electronic/Club/Urban/Dance.House/Techno House");
		table.put("3.6.8.5", "Content.Music and Dance.Electronic/Club/Urban/Dance.Rave");
		table.put("3.6.8.6", "Content.Music and Dance.Electronic/Club/Urban/Dance.Jungle/Tribal");
		table.put("3.6.8.7", "Content.Music and Dance.Electronic/Club/Urban/Dance.Trance");
		table.put("3.6.8.8", "Content.Music and Dance.Electronic/Club/Urban/Dance.Punk");
		table.put("3.6.8.9", "Content.Music and Dance.Electronic/Club/Urban/Dance.Garage/Psychadelic");
		table.put("3.6.8.10", "Content.Music and Dance.Electronic/Club/Urban/Dance.Metal/Death metal/Pop metal");
		table.put("3.6.8.11", "Content.Music and Dance.Electronic/Club/Urban/Dance.Drum and Bass");
		table.put("3.6.8.12", "Content.Music and Dance.Electronic/Club/Urban/Dance.Pranks");
		table.put("3.6.8.13", "Content.Music and Dance.Electronic/Club/Urban/Dance.Grunge");
		table.put("3.6.8.14", "Content.Music and Dance.Electronic/Club/Urban/Dance.Dance/Dance-pop");
		table.put("3.6.9", "Content.Music and Dance.World/Traditional/Ethnic/Folk music");
		table.put("3.6.9.1", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.Africa");
		table.put("3.6.9.2", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.Asia");
		table.put("3.6.9.3", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.Australia/Oceania");
		table.put("3.6.9.4", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.Caribbean");
		table.put("3.6.9.5", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.Europe");
		table.put("3.6.9.6", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.Latin America");
		table.put("3.6.9.7", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.Middle East");
		table.put("3.6.9.8", "Content.Music and Dance.World/Traditional/Ethnic/Folk music.North America");
		table.put("3.6.10", "Content.Music and Dance.Hit-Chart/Song Requests ");
		table.put("3.6.11", "Content.Music and Dance.Children's Songs ");
		table.put("3.6.12", "Content.Music and Dance.Event music");
		table.put("3.6.12.1", "Content.Music and Dance.Event music.Wedding");
		table.put("3.6.12.2", "Content.Music and Dance.Event music.Sports");
		table.put("3.6.12.3", "Content.Music and Dance.Event music.Ceremonial/Chants");
		table.put("3.6.13", "Content.Music and Dance.Spoken");
		table.put("3.6.14", "Content.Music and Dance.Dance");
		table.put("3.6.14.1", "Content.Music and Dance.Dance.Ballet");
		table.put("3.6.14.2", "Content.Music and Dance.Dance.Tap");
		table.put("3.6.14.3", "Content.Music and Dance.Dance.Modern");
		table.put("3.6.14.4", "Content.Music and Dance.Dance.Classical");
		table.put("3.7", "Content.Interactive Games");
		table.put("3.7.1", "Content.Interactive Games.Content games categories");
		table.put("3.7.1.1", "Content.Interactive Games.Content games categories.Action");
		table.put("3.7.1.2", "Content.Interactive Games.Content games categories.Adventure");
		table.put("3.7.1.3", "Content.Interactive Games.Content games categories.Fighting");
		table.put("3.7.1.4", "Content.Interactive Games.Content games categories.Online");
		table.put("3.7.1.5", "Content.Interactive Games.Content games categories.Platform");
		table.put("3.7.1.6", "Content.Interactive Games.Content games categories.Puzzle");
		table.put("3.7.1.7", "Content.Interactive Games.Content games categories.RPG/MUDs");
		table.put("3.7.1.8", "Content.Interactive Games.Content games categories.Racing");
		table.put("3.7.1.9", "Content.Interactive Games.Content games categories.Simulation");
		table.put("3.7.1.10", "Content.Interactive Games.Content games categories.Sports");
		table.put("3.7.1.11", "Content.Interactive Games.Content games categories.Strategy");
		table.put("3.7.1.12", "Content.Interactive Games.Content games categories.Wrestling");
		table.put("3.7.1.13", "Content.Interactive Games.Content games categories.Classic/Retro");
		table.put("3.8.1.14", "Content.Interactive Games.Content games categories.Other");
		table.put("3.7.2", "Content.Interactive Games.Style");
		table.put("3.7.2.1", "Content.Interactive Games.Style.Logic based");
		table.put("3.7.2.2", "Content.Interactive Games.Style.Word games");
		table.put("3.7.2.3", "Content.Interactive Games.Style.Positional");
		table.put("3.7.2.4", "Content.Interactive Games.Style.Board games");
		table.put("3.7.2.5", "Content.Interactive Games.Style.Text environments");
		table.put("3.7.2.6", "Content.Interactive Games.Style.Dynamic 2D/3D graphics");
		table.put("3.7.2.7", "Content.Interactive Games.Style.Non-linear video");

	    // Intended audience table
		table.put("4", "Intended audience");
		table.put("4.1", "Intended audience.General audience");
		table.put("4.2", "Intended audience.Age groups");
		table.put("4.2.1", "Intended audience.Age groups.Children");
		table.put("4.2.1.1", "Intended audience.Age groups.Children.Age 4-7");
		table.put("4.2.1.2", "Intended audience.Age groups.Children.Age 8-13");
		table.put("4.2.1.3", "Intended audience.Age groups.Children.Age 14-15");
    table.put("4.2.2", "Intended audience.Age groups.Young Adults");    
    table.put("4.2.2.1", "Intended audience.Age groups.Young Adults.Age 16-17");    
    table.put("4.2.2.2", "Intended audience.Age groups.Young Adults.Age 18-20");        
    table.put("4.2.2.3", "Intended audience.Age groups.Young Adults.Age 21-24");    
    table.put("4.2.3", "Intended audience.Age groups.Adults");
    table.put("4.2.3.1", "Intended audience.Age groups.Adults.Age 25-34");
    table.put("4.2.3.2", "Intended audience.Age groups.Adults.Age 35-44");
    table.put("4.2.3.3", "Intended audience.Age groups.Adults.Age 45-54");
    table.put("4.2.3.4", "Intended audience.Age groups.Adults.Age 55-64");
    table.put("4.2.3.5", "Intended audience.Age groups.Adults.Age 65+");
    table.put("4.2.3.6", "Intended audience.Age groups.Adults.Specific single age");
    table.put("4.2.4", "Intended audience.Age groups.All ages");
		table.put("4.3", "Intended audience.Social groups");
		table.put("4.3.1", "Intended audience.Social groups.Ethnic");
		table.put("4.3.1.1", "Intended audience.Social groups.Ethnic.Immigrant groups");
		table.put("4.3.1.2", "Intended audience.Social groups.Ethnic.Indiginous");
		table.put("4.3.2", "Intended audience.Social groups.Religious");
		table.put("4.4", "Intended audience.Occupational groups");
		table.put("4.4.1", "Intended audience.Occupational groups.AB");
		table.put("4.4.1.1", "Intended audience.Occupational groups.AB.A");
		table.put("4.4.1.2", "Intended audience.Occupational groups.AB.B");
		table.put("4.4.2", "Intended audience.Occupational groups.C1C2");
		table.put("4.4.2.1", "Intended audience.Occupational groups.C1C2.C1");
		table.put("4.4.2.2", "Intended audience.Occupational groups.C1C2.C2");
		table.put("4.4.3", "Intended audience.Occupational groups.DE");
		table.put("4.4.3.1", "Intended audience.Occupational groups.DE.D");
		table.put("4.4.3.2", "Intended audience.Occupational groups.DE.E");
		table.put("4.5", "Intended audience.Other special interest/Occupational groups");
		table.put("4.6", "Intended audience.Gender");
		table.put("4.6.1", "Intended audience.Gender.Primarily for males");
 		table.put("4.6.2", "Intended audience.Gender.Primarily for females");
 		table.put("4.6.3", "Intended audience.Gender.For males and females");
		table.put("4.7", "Intended audience.Geographical");
		table.put("4.7.1", "Intended audience.Geographical.Universal");
		table.put("4.7.2", "Intended audience.Geographical.Continental");
		table.put("4.7.3", "Intended audience.Geographical.National");
		table.put("4.7.4", "Intended audience.Geographical.Regional");
		table.put("4.7.5", "Intended audience.Geographical.Local");
		table.put("4.8", "Intended audience.Education level");
		table.put("4.8.1", "Intended audience.Education level.Primary");
		table.put("4.8.2", "Intended audience.Education level.Secondary");
		table.put("4.8.3", "Intended audience.Education level.Tertiary");
		table.put("4.8.4", "Intended audience.Education level.Post Graduate/Life-long learning");
		table.put("4.9", "Intended audience.Lifestyle stages");
		table.put("4.9.1", "Intended audience.Lifestyle stages.Single");
		table.put("4.9.2", "Intended audience.Lifestyle stages.Couple");
		table.put("4.9.3", "Intended audience.Lifestyle stages.Family with Children 0-3");
		table.put("4.9.4", "Intended audience.Lifestyle stages.Family with Children 4-7");
		table.put("4.9.5", "Intended audience.Lifestyle stages.Family with Children 8-15");
		table.put("4.9.6", "Intended audience.Lifestyle stages.Family with Children 16+");
		table.put("4.9.7", "Intended audience.Lifestyle stages.Empty Nester");
		table.put("4.9.8", "Intended audience.Lifestyle stages.Retired");

    // Origination table
		table.put("5", "Origination");
		table.put("5.1", "Origination.Studio");
		table.put("5.1.1", "Origination.Studio.Live");
		table.put("5.1.2", "Origination.Studio.As live");
		table.put("5.1.3", "Origination.Studio.Edited");
		table.put("5.2", "Origination.Made on location");
		table.put("5.2.1", "Origination.Made on location.Live");
		table.put("5.2.2", "Origination.Made on location.As live");
		table.put("5.2.3", "Origination.Made on location.Edited");
		table.put("5.3", "Origination.Cinema industry originated");
		table.put("5.4", "Origination.Made on film");
		table.put("5.5", "Origination.Home video");
		table.put("5.6", "Origination.Multimedia format");

	  // Content alert table
		table.put("6", "Content alert");
    table.put("6.0","Content alert.Alert not required");
    table.put("6.0.1","Content alert.Alert not required.No content that requires alerting in any of the categories below");
    table.put("6.1","Content alert.Sex");
    table.put("6.1.1","Content alert.Sex.No sex descriptors");
    table.put("6.1.2","Content alert.Sex.Obscured or implied sexual activity");
    table.put("6.1.3","Content alert.Sex.Frank portrayal of sex and sexuality");
    table.put("6.1.4","Content alert.Sex.Scenes of explicit sexual behaviour suitable for adults only");
    table.put("6.1.5","Content alert.Sex.Sexual Violence");
    table.put("6.2","Content alert.Nudity");
    table.put("6.2.1","Content alert.Nudity.No nudity descriptors");
    table.put("6.2.2","Content alert.Nudity.Partial nudity");
    table.put("6.2.3","Content alert.Nudity.Full frontal nudity");
    table.put("6.3","Content alert.Violence - Human Beings");
    table.put("6.3.1","Content alert.Violence - Human Beings.No violence descriptors human beings");
    table.put("6.3.2","Content alert.Violence - Human Beings.Deliberate infliction of pain to human beings");
    table.put("6.3.3","Content alert.Violence - Human Beings.Infliction of strong psychological or physical pain to human beings");
    table.put("6.3.4","Content alert.Violence - Human Beings.Deliberate killing of human beings");
    table.put("6.4","Content alert.Violence - Animals");
    table.put("6.4.1","Content alert.Violence - Animals.No violence descriptors animals");
    table.put("6.4.2","Content alert.Violence - Animals.Deliberate infliction of pain to animals");
    table.put("6.4.3","Content alert.Violence - Animals.Deliberate killing of animals");
    table.put("6.5","Content alert.Violence - Fantasy Characters");
    table.put("6.5.1","Content alert.Violence - Fantasy Characters.No violence descriptors");
    table.put("6.5.2","Content alert.Violence - Fantasy Characters.Deliberate infliction of pain to fantasy characters (including animation)");
    table.put("6.5.3","Content alert.Violence - Fantasy Characters.Deliberate killing of fantasy characters (including animation)");
    table.put("6.6","Content alert.Language");
    table.put("6.6.1","Content alert.Language.No language descriptors");
    table.put("6.6.2","Content alert.Language.Occasional use of mild swear words and profanities");
    table.put("6.6.3","Content alert.Language.Frequent use of mild swear words and profanities");
    table.put("6.6.4","Content alert.Language.Occasional use of very strong language");
    table.put("6.6.5","Content alert.Language.Frequent use of very strong language");
    table.put("6.7","Content alert.Disturbing Scenes");
    table.put("6.7.1","Content alert.Disturbing Scenes.No disturbing scenes descriptors");
    table.put("6.7.2","Content alert.Disturbing Scenes.Factual material that may cause distress");
    table.put("6.7.3","Content alert.Disturbing Scenes.Mild scenes of blood and gore");
    table.put("6.7.4","Content alert.Disturbing Scenes.Severe scenes of blood and gore");
    table.put("6.7.5","Content alert.Disturbing Scenes.Scenes with extreme horror effects");
    table.put("6.8","Content alert.Discrimination");
    table.put("6.8.1","Content alert.Discrimination.No discrimination descriptors");
    table.put("6.8.2","Content alert.Discrimination.Deliberate discrimination or the portrayal of deliberate discrimination");
    table.put("6.9","Content alert.Illegal Drugs");
    table.put("6.9.1","Content alert.Illegal Drugs.No illegal drugs descriptors");
    table.put("6.9.2","Content alert.Illegal Drugs.Portrayal of illegal drug use");
    table.put("6.9.3","Content alert.Illegal Drugs.Portrayal of illegal drug use with instructive detail");
    table.put("6.10","Content alert.Strobing");
    table.put("6.10.1","Content alert.Strobing.No strobing");
    table.put("6.10.2","Content alert.Strobing.Strobing that could impact on those suffering from Photosensitive epilepsy");

    // Media type table
		table.put("7", "Media type");
		table.put("7.1", "Media type.Linear");
		table.put("7.1.1", "Media type.Linear.Audio only");
		table.put("7.1.2", "Media type.Linear.Video only");
		table.put("7.1.3", "Media type.Linear.Audio and video");
		table.put("7.1.4", "Media type.Linear.Multimedia");
		table.put("7.1.4.1", "Media type.Linear.Multimedia.Text");
		table.put("7.1.4.2", "Media type.Linear.Multimedia.Graphics");
		table.put("7.1.4.3", "Media type.Linear.Multimedia.Application");
		table.put("7.2", "Media type.Non-linear");
		table.put("7.2.1", "Media type.Non-linear.Audio only");
		table.put("7.2.2", "Media type.Non-linear.Video only");
		table.put("7.2.3", "Media type.Non-linear.Audio and video");
		table.put("7.2.4", "Media type.Non-linear.Multimedia");
		table.put("7.2.4.1", "Media type.Non-linear.Multimedia.Text");
		table.put("7.2.4.2", "Media type.Non-linear.Multimedia.Graphics");
		table.put("7.2.4.3", "Media type.Non-linear.Multimedia.Application");
		table.put("7.3", "Media type.Audio video enhancements");
		table.put("7.3.1", "Media type.Audio video enhancements.Linear with non-sync");
		table.put("7.3.2", "Media type.Audio video enhancements.Linear with sync");
		table.put("7.3.3", "Media type.Audio video enhancements.Multi stream audio");
		table.put("7.3.4", "Media type.Audio video enhancements.Multi stream video");
		table.put("7.3.5", "Media type.Audio video enhancements.Non-linear one stream AV show");
		table.put("7.3.6", "Media type.Audio video enhancements.Non-linear multi stream");
		table.put("7.3.7", "Media type.Audio video enhancements.Hybrid NVOD");
		table.put("7.3.8", "Media type.Audio video enhancements.Mix and match");
		table.put("7.3.9", "Media type.Audio video enhancements.Parallel 'layer controlled' audio or video support");
		table.put("7.3.10", "Media type.Audio video enhancements.Linear broadcast with online insertions");
		table.put("7.3.11", "Media type.Audio video enhancements.Other");

    // Atmosphere table
		table.put("8", "Atmosphere");
		table.put("8.1", "Atmosphere.Alternative");
		table.put("8.2", "Atmosphere.Analytical");
		table.put("8.3", "Atmosphere.Astonishing");
		table.put("8.4", "Atmosphere.Ambitious");
		table.put("8.5", "Atmosphere.Black");
		table.put("8.6", "Atmosphere.Breathtaking");
		table.put("8.7", "Atmosphere.Chilling");
		table.put("8.8", "Atmosphere.Coarse");
		table.put("8.9", "Atmosphere.Compelling");
		table.put("8.10", "Atmosphere.Confrontational");
		table.put("8.11", "Atmosphere.Contemporary");
		table.put("8.12", "Atmosphere.Crazy");
		table.put("8.13", "Atmosphere.Cutting edge");
		table.put("8.14", "Atmosphere.Eclectic");
		table.put("8.15", "Atmosphere.Edifying");
		table.put("8.16", "Atmosphere.Exciting");
		table.put("8.17", "Atmosphere.Fast-moving");
		table.put("8.18", "Atmosphere.Frantic");
		table.put("8.19", "Atmosphere.Fun");
		table.put("8.20", "Atmosphere.Gripping");
		table.put("8.21", "Atmosphere.Gritty");
		table.put("8.22", "Atmosphere.Gutsy");
		table.put("8.23", "Atmosphere.Happy");
		table.put("8.24", "Atmosphere.Heart-rending");
		table.put("8.25", "Atmosphere.Heart warming");
		table.put("8.26", "Atmosphere.Hot");
		table.put("8.27", "Atmosphere.Humorous");
		table.put("8.28", "Atmosphere.Innovative");
		table.put("8.29", "Atmosphere.Insightful");
		table.put("8.30", "Atmosphere.Inspirational");
		table.put("8.31", "Atmosphere.Intriguing");
		table.put("8.32", "Atmosphere.Irreverant");
		table.put("8.33", "Atmosphere.Laid back");
		table.put("8.34", "Atmosphere.Outrageous");
		table.put("8.35", "Atmosphere.Peaceful");
		table.put("8.36", "Atmosphere.Powerful");
		table.put("8.37", "Atmosphere.Practical");
		table.put("8.38", "Atmosphere.Rollercoaster");
		table.put("8.39", "Atmosphere.Romantic");
		table.put("8.40", "Atmosphere.Rousing");
		table.put("8.41", "Atmosphere.Sad");
		table.put("8.42", "Atmosphere.Satirical");
		table.put("8.43", "Atmosphere.Serious");
		table.put("8.44", "Atmosphere.Sexy");
		table.put("8.45", "Atmosphere.Shocking");
		table.put("8.46", "Atmosphere.Silly");
		table.put("8.47", "Atmosphere.Spooky");
		table.put("8.48", "Atmosphere.Stunning");
		table.put("8.49", "Atmosphere.Stylish");
		table.put("8.50", "Atmosphere.Terrifying");
		table.put("8.51", "Atmosphere.Thriller");
		table.put("8.52", "Atmosphere.Violent");
		table.put("8.53", "Atmosphere.Wacky");

	}

	//to prevent initialization
	protected TVAnytimeGenreToolbox()
	{
	}

	/**
	 * getNameHeirarchy - converts a numbered heirarchy to its corresponding name heirarchy
	 *
	 * @param	numberHeirarchy	the numbered heirarchy String to be converted
	 * @return 	the name heirarchy String
	 */
	public static String getNameHierarchy(String numberHierarchy)
	{
    if (numberHierarchy != null) return (String)table.get(numberHierarchy);
    else return "<null>";
	}

	/**
	 * getNumberHierarchy - converts a named hierarchy to its corresponding number hierarchy
	 *
	 * @param 	nameHierarchy	the named hierarchy String to be converted
	 * @return 	the name hierarchy String
	 */
	public static String getNumberHierarchy(String nameHierarchy)
	{
		String genreNumber = "";

		//list all the number heirarchies
		Enumeration genreNumbers = table.keys();

		//go through all the number heirarchies looking for the matching name hierarchy
		while (genreNumbers.hasMoreElements())
		{
			genreNumber = (String)genreNumbers.nextElement();

			if (nameHierarchy.equalsIgnoreCase((String)table.get(genreNumber)))
				return genreNumber;
		}

		return null;
	}

	/**
	 * isValid - verifies whether a genre hierarchy is included in the current genre scheme
	 *
	 * @param 	genreHierarchy	the hierarchy (number or name) String to be verified. If a name
	 * hierarchy is passed it must have correct case
	 *
	 * @return 	whether the hierarchy is valid as a boolean object
	 */
	public static boolean isValid(String hierarchy)
	{
		if (table.containsKey(hierarchy))
			return true;

		else if(table.contains(hierarchy))
			return true;

		else return false;

	}

	/**
	 * getSubGenres - from a genre heading(in numbered hierarchy form) get any sub-genres
	 *
	 * @param 	genreParent	the genre heading (number hierarchy String) from which sub genres are to be found
	 * @return	a Vector of the numbered hierarchy Strings of the subgenre objects
	 */
	public static Vector getSubGenres(String genreParent)
	{
		Vector genres = new Vector(0,1);
		String genreNumber = "";

		//list all number heirarchies
		Enumeration genreNumbers = table.keys();

		//look through all the name heirarchies corresponding to the name heirarchies
		while (genreNumbers.hasMoreElements())
		{
			genreNumber = (String)genreNumbers.nextElement();
			//if the name hierarchy starts with the parent genre then add it to the Vector
			if (genreNumber.startsWith(genreParent+".")) {
				//genres.addElement(genreNumber);
        genres.insertElementAt(genreNumber,0);
      }
		}

		return genres;
	}

	/**
	* getTopLevelSubGenres - from a genre heading(in numbered hierarchy form) get
	* the immediate child sub-genres only.
	*
	* @param 	genreParent	the genre heading (number hierarchy) from which sub genres
	*          are to be found. If null then returns all top-level genres only.
	* @return	a Vector of the numbered hierarchy Strings of the subgenre objects
	*/
	public static Vector getTopLevelSubGenres(String genreParent)
	{
		Vector genres = new Vector(0,1);
		String genreNumber = "";
    boolean toplevel = false; // If set then return all top-level genres.

    if (genreParent==null) toplevel=true;
    else if (genreParent.length()==0) toplevel=true;

		//list all number heirarchies
		Enumeration genreNumbers = table.keys();

		//look through all the name heirarchies corresponding to the name heirarchies
		while (genreNumbers.hasMoreElements())
		{
			genreNumber = (String)genreNumbers.nextElement();
      if (toplevel) {
        if (genreNumber.indexOf(".")<0) {
          //genres.addElement(genreNumber);
          genres.insertElementAt(genreNumber,0);
        }
      }
      else {
        //if the name hierarchy starts with the parent genre then add it to the Vector
        if (genreNumber.startsWith(genreParent))
        {
          //check for only top level results i.e. with a number hierarchy longer than
          //the parent to avoid returning the parent and with no further dot separaters
          //after the level AFTER the parent level e.g. parent = 3.2, 3.2 will not be
          //returned AND 3.2.1.1 WON'T be returned
          if ((genreNumber.length() > genreParent.length()) &&
            (genreNumber.indexOf(".", genreParent.length()+1) == -1) &&
            ((new Character(genreNumber.charAt(genreParent.length()))).equals(new Character('.')))) {
              //genres.addElement(genreNumber);
              genres.insertElementAt(genreNumber,0);
            }

        }
      }
		}

		return genres;
	}

	/**
	 * findGenre - returns the named hierarchy of any genres containing the specifed genre name
	 *
	 * @param	genreName	the genre name to be searched for
	 *
	 * @return	a Vector of the Genre name heirarchy strings containing the Genre name
	 */
	public static Vector findGenre(String searchName)
	{
		Vector genres = new Vector(0,1);
		String genreName = "";
		searchName = searchName.toLowerCase();
		//list all the name heirarchies
		Enumeration genreNames = table.elements();
		while (genreNames.hasMoreElements())
		{
			genreName = ((String)genreNames.nextElement()).toLowerCase();

			//if the name hierarchy contains the Genre name we're looking for
			if (genreName.indexOf(searchName)>= 0)
				genres.addElement(genreName);
		}

		return genres;
	}

	/**
	 * getParent - returns the Genre hierarchy of the Genre one level up the Genre
	 * hierarchy from the the Genre hierarchy passed
	 *
	 * @param	hierarchy	the hierarchy String of the Genre whose parent is desired (can be numbered
	 * or named)
	 *
	 * @return 	the numbered Genre hierarchy String of the parent of the Genre passed
	 */
	public static String getParent(String hierarchy)
	{
		int leafLevelPoint = hierarchy.lastIndexOf(".");
		if (leafLevelPoint < 0)
			return null;
		else
			return hierarchy.substring(0,leafLevelPoint);

	}

	/**
	 * getNumLevels - returns the number of levels used in the Genre hierarchy passed
	 *
	 * @param	hierarchy 	the numbered or named hierarchy of the Genre object whose number of levels is desired
	 *
	 * @return 	the number of levels in the passed Genre hierarchy
	 */
	public static int getNumLevels(String hierarchy)
	{
		int levelNum = 0;
		int dotPoint = 0;
		while (dotPoint >= 0)
		{
			dotPoint = hierarchy.indexOf(".", dotPoint+1);
			levelNum++;
		}

		return levelNum;
	}
}