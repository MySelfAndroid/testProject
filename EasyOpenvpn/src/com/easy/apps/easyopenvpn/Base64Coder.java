package com.easy.apps.easyopenvpn;
/**
* A Base64 Encoder/Decoder.
*
* <p>
* This class is used to encode and decode data in Base64 format as described in RFC 1521.
*
* <p>
* This is "Open Source" software and released under the <a href="http://www.gnu.org/licenses/lgpl.html">GNU/LGPL</a> license.<br>
* It is provided "as is" without warranty of any kind.<br>
* Copyright 2003: Christian d'Heureuse, Inventec Informatik AG, Switzerland.<br>
* Home page: <a href="http://www.source-code.biz">www.source-code.biz</a><br>
*
* <p>
* Version history:<br>
* 2003-07-22 Christian d'Heureuse (chdh): Module created.<br>
* 2005-08-11 chdh: Lincense changed from GPL to LGPL.<br>
* 2006-11-21 chdh:<br>
*  &nbsp; Method encode(String) renamed to encodeString(String).<br>
*  &nbsp; Method decode(String) renamed to decodeString(String).<br>
*  &nbsp; New method encode(byte[],int) added.<br>
*  &nbsp; New method decode(String) added.<br>
*/

public class Base64Coder {

// Mapping table from 6-bit nibbles to Base64 characters.
	
public static void main(String args[]){
	System.out.println(Base64Coder.decodeString("IyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIw0KIyBPcGVuVlBOIDIuMCBTYW1wbGUgQ29uZmlndXJhdGlvbiBGaWxlDQojIGZvciBQYWNrZXRpWCBWUE4gLyBTb2Z0RXRoZXIgVlBOIFNlcnZlcg0KIyANCiMgISEhIEFVVE8tR0VORVJBVEVEIEJZIFNPRlRFVEhFUiBWUE4gU0VSVkVSIE1BTkFHRU1FTlQgVE9PTCAhISENCiMgDQojICEhISBZT1UgSEFWRSBUTyBSRVZJRVcgSVQgQkVGT1JFIFVTRSBBTkQgTU9ESUZZIElUIEFTIE5FQ0VTU0FSWSAhISENCiMgDQojIFRoaXMgY29uZmlndXJhdGlvbiBmaWxlIGlzIGF1dG8tZ2VuZXJhdGVkLiBZb3UgbWlnaHQgdXNlIHRoaXMgY29uZmlnIGZpbGUNCiMgaW4gb3JkZXIgdG8gY29ubmVjdCB0byB0aGUgUGFja2V0aVggVlBOIC8gU29mdEV0aGVyIFZQTiBTZXJ2ZXIuDQojIEhvd2V2ZXIsIGJlZm9yZSB5b3UgdHJ5IGl0LCB5b3Ugc2hvdWxkIHJldmlldyB0aGUgZGVzY3JpcHRpb25zIG9mIHRoZSBmaWxlDQojIHRvIGRldGVybWluZSB0aGUgbmVjZXNzaXR5IHRvIG1vZGlmeSB0byBzdWl0YWJsZSBmb3IgeW91ciByZWFsIGVudmlyb25tZW50Lg0KIyBJZiBuZWNlc3NhcnksIHlvdSBoYXZlIHRvIG1vZGlmeSBhIGxpdHRsZSBhZGVxdWF0ZWx5IG9uIHRoZSBmaWxlLg0KIyBGb3IgZXhhbXBsZSwgdGhlIElQIGFkZHJlc3Mgb3IgdGhlIGhvc3RuYW1lIGFzIGEgZGVzdGluYXRpb24gVlBOIFNlcnZlcg0KIyBzaG91bGQgYmUgY29uZmlybWVkLg0KIyANCiMgTm90ZSB0aGF0IHRvIHVzZSBPcGVuVlBOIDIuMCwgeW91IGhhdmUgdG8gcHV0IHRoZSBjZXJ0aWZpY2F0aW9uIGZpbGUgb2YNCiMgdGhlIGRlc3RpbmF0aW9uIFZQTiBTZXJ2ZXIgb24gdGhlIE9wZW5WUE4gQ2xpZW50IGNvbXB1dGVyIHdoZW4geW91IHVzZSB0aGlzDQojIGNvbmZpZyBmaWxlLiBQbGVhc2UgcmVmZXIgdGhlIGJlbG93IGRlc2NyaXB0aW9ucyBjYXJlZnVsbHkuDQoNCg0KIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIw0KIyBTcGVjaWZ5IHRoZSB0eXBlIG9mIHRoZSBsYXllciBvZiB0aGUgVlBOIGNvbm5lY3Rpb24uDQojIA0KIyBUbyBjb25uZWN0IHRvIHRoZSBWUE4gU2VydmVyIGFzIGEgIlJlbW90ZS1BY2Nlc3MgVlBOIENsaWVudCBQQyIsDQojICBzcGVjaWZ5ICdkZXYgdHVuJy4gKExheWVyLTMgSVAgUm91dGluZyBNb2RlKQ0KIw0KIyBUbyBjb25uZWN0IHRvIHRoZSBWUE4gU2VydmVyIGFzIGEgYnJpZGdpbmcgZXF1aXBtZW50IG9mICJTaXRlLXRvLVNpdGUgVlBOIiwNCiMgIHNwZWNpZnkgJ2RldiB0YXAnLiAoTGF5ZXItMiBFdGhlcm5ldCBCcmlkZ2luZSBNb2RlKQ0KDQpkZXYgdHVuDQoNCg0KIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIw0KIyBTcGVjaWZ5IHRoZSB1bmRlcmx5aW5nIHByb3RvY29sIGJleW9uZCB0aGUgSW50ZXJuZXQuDQojIE5vdGUgdGhhdCB0aGlzIHNldHRpbmcgbXVzdCBiZSBjb3JyZXNwb25kIHdpdGggdGhlIGxpc3RlbmluZyBzZXR0aW5nIG9uDQojIHRoZSBWUE4gU2VydmVyLg0KIyANCiMgU3BlY2lmeSBlaXRoZXIgJ3Byb3RvIHRjcCcgb3IgJ3Byb3RvIHVkcCcuDQoNCnByb3RvIHRjcA0KDQoNCiMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMNCiMgVGhlIGRlc3RpbmF0aW9uIGhvc3RuYW1lIC8gSVAgYWRkcmVzcywgYW5kIHBvcnQgbnVtYmVyIG9mDQojIHRoZSB0YXJnZXQgVlBOIFNlcnZlci4NCiMgDQojIFlvdSBoYXZlIHRvIHNwZWNpZnkgYXMgJ3JlbW90ZSA8SE9TVE5BTUU+IDxQT1JUPicuIFlvdSBjYW4gYWxzbw0KIyBzcGVjaWZ5IHRoZSBJUCBhZGRyZXNzIGluc3RlYWQgb2YgdGhlIGhvc3RuYW1lLg0KIyANCiMgTm90ZSB0aGF0IHRoZSBhdXRvLWdlbmVyYXRlZCBiZWxvdyBob3N0bmFtZSBhcmUgYSAiYXV0by1kZXRlY3RlZA0KIyBJUCBhZGRyZXNzIiBvZiB0aGUgVlBOIFNlcnZlci4gWW91IGhhdmUgdG8gY29uZmlybSB0aGUgY29ycmVjdG5lc3MNCiMgYmVmb3JlaGFuZC4NCiMgDQojIFdoZW4geW91IHdhbnQgdG8gY29ubmVjdCB0byB0aGUgVlBOIFNlcnZlciBieSB1c2luZyBUQ1AgcHJvdG9jb2wsDQojIHRoZSBwb3J0IG51bWJlciBvZiB0aGUgZGVzdGluYXRpb24gVENQIHBvcnQgc2hvdWxkIGJlIHNhbWUgYXMgb25lIG9mDQojIHRoZSBhdmFpbGFibGUgVENQIGxpc3RlbmVycyBvbiB0aGUgVlBOIFNlcnZlci4NCiMgDQojIFdoZW4geW91IHVzZSBVRFAgcHJvdG9jb2wsIHRoZSBwb3J0IG51bWJlciBtdXN0IHNhbWUgYXMgdGhlIGNvbmZpZ3VyYXRpb24NCiMgc2V0dGluZyBvZiAiT3BlblZQTiBTZXJ2ZXIgQ29tcGF0aWJsZSBGdW5jdGlvbiIgb24gdGhlIFZQTiBTZXJ2ZXIuDQoNCnJlbW90ZSAxMjIuMTMyLjE1OC4yNDkgMTc0Mw0KDQoNCiMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMNCiMgVGhlIEhUVFAvSFRUUFMgcHJveHkgc2V0dGluZy4NCiMgDQojIE9ubHkgaWYgeW91IGhhdmUgdG8gdXNlIHRoZSBJbnRlcm5ldCB2aWEgYSBwcm94eSwgdW5jb21tZW50IHRoZSBiZWxvdw0KIyB0d28gbGluZXMgYW5kIHNwZWNpZnkgdGhlIHByb3h5IGFkZHJlc3MgYW5kIHRoZSBwb3J0IG51bWJlci4NCiMgSW4gdGhlIGNhc2Ugb2YgdXNpbmcgcHJveHktYXV0aGVudGljYXRpb24sIHJlZmVyIHRoZSBPcGVuVlBOIG1hbnVhbC4NCg0KO2h0dHAtcHJveHktcmV0cnkNCjtodHRwLXByb3h5IFtwcm94eSBzZXJ2ZXJdIFtwcm94eSBwb3J0XQ0KDQoNCiMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMNCiMgVGhlIGVuY3J5cHRpb24gYW5kIGF1dGhlbnRpY2F0aW9uIGFsZ29yaXRobS4NCiMgDQojIERlZmF1bHQgc2V0dGluZyBpcyBnb29kLiBNb2RpZnkgaXQgYXMgeW91IHByZWZlci4NCiMgV2hlbiB5b3Ugc3BlY2lmeSBhbiB1bnN1cHBvcnRlZCBhbGdvcml0aG0sIHRoZSBlcnJvciB3aWxsIG9jY3VyLg0KIyANCiMgVGhlIHN1cHBvcnRlZCBhbGdvcml0aG1zIGFyZSBhcyBmb2xsb3dzOg0KIyAgY2lwaGVyOiBbTlVMTC1DSVBIRVJdIE5VTEwgQUVTLTEyOC1DQkMgQUVTLTE5Mi1DQkMgQUVTLTI1Ni1DQkMgQkYtQ0JDDQojICAgICAgICAgIENBU1QtQ0JDIENBU1Q1LUNCQyBERVMtQ0JDIERFUy1FREUtQ0JDIERFUy1FREUzLUNCQyBERVNYLUNCQw0KIyAgICAgICAgICBSQzItNDAtQ0JDIFJDMi02NC1DQkMgUkMyLUNCQw0KIyAgYXV0aDogICBTSEEgU0hBMSBNRDUgTUQ0IFJNRDE2MA0KDQpjaXBoZXIgQUVTLTEyOC1DQkMNCmF1dGggU0hBMQ0KDQoNCiMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMNCiMgT3RoZXIgcGFyYW1ldGVycyBuZWNlc3NhcnkgdG8gY29ubmVjdCB0byB0aGUgVlBOIFNlcnZlci4NCiMgDQojIEl0IGlzIG5vdCByZWNvbW1lbmRlZCB0byBtb2RpZnkgaXQgdW5sZXNzIHlvdSBoYXZlIGEgcGFydGljdWxhciBuZWVkLg0KDQpyZXNvbHYtcmV0cnkgaW5maW5pdGUNCm5vYmluZA0KcGVyc2lzdC1rZXkNCnBlcnNpc3QtdHVuDQpjbGllbnQNCnZlcmIgMw0KI2F1dGgtdXNlci1wYXNzDQoNCg0KIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIw0KIyBUaGUgY2VydGlmaWNhdGUgZmlsZSBvZiB0aGUgZGVzdGluYXRpb24gVlBOIFNlcnZlci4NCiMgDQojIFRoZSBDQSBjZXJ0aWZpY2F0ZSBmaWxlIGlzIGVtYmVkZGVkIGluIHRoZSBpbmxpbmUgZm9ybWF0Lg0KIyBZb3UgY2FuIHJlcGxhY2UgdGhpcyBDQSBjb250ZW50cyBpZiBuZWNlc3NhcnkuDQojIFBsZWFzZSBub3RlIHRoYXQgaWYgdGhlIHNlcnZlciBjZXJ0aWZpY2F0ZSBpcyBub3QgYSBzZWxmLXNpZ25lZCwgeW91IGhhdmUgdG8NCiMgc3BlY2lmeSB0aGUgc2lnbmVyJ3Mgcm9vdCBjZXJ0aWZpY2F0ZSAoQ0EpIGhlcmUuDQoNCjxjYT4NCi0tLS0tQkVHSU4gQ0VSVElGSUNBVEUtLS0tLQ0KTUlJRFZEQ0NBanlnQXdJQkFnSURBalJXTUEwR0NTcUdTSWIzRFFFQkJRVUFNRUl4Q3pBSkJnTlZCQVlUQWxWVA0KTVJZd0ZBWURWUVFLRXcxSFpXOVVjblZ6ZENCSmJtTXVNUnN3R1FZRFZRUURFeEpIWlc5VWNuVnpkQ0JIYkc5aQ0KWVd3Z1EwRXdIaGNOTURJd05USXhNRFF3TURBd1doY05Nakl3TlRJeE1EUXdNREF3V2pCQ01Rc3dDUVlEVlFRRw0KRXdKVlV6RVdNQlFHQTFVRUNoTU5SMlZ2VkhKMWMzUWdTVzVqTGpFYk1Ca0dBMVVFQXhNU1IyVnZWSEoxYzNRZw0KUjJ4dlltRnNJRU5CTUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUEyc3dZWXpEOQ0KOUJjakdsWitXOTg4YkRqa2NiZDRrZFM4b2RoTStLaER0Z1BwVFNFSENJamFXQzltT1NtOUJYaUxuVGpvQmJkcQ0KZm5HazVzUmdwckR2Z09TSktBK2VKZGJ0Zy9PdHBwSEhtTWxDR0RVVW5hMllScEl1VDhyeGgwUEJGcFZYTFZEdg0KaVMyQWVsZXQ4dTVmYTlJQWpia1UrQlFWTmRuQVJxTjdjc2lSdjhsVks4M1FsejZjSm1UTTM4NkRHWEhLVHViVQ0KMVh1cEdjMVYzc2pzMGw0NFUrVmNUNHd0L2xBak52eG01c3VPcERrWkFMZVZBam1SQ3c3K09DN1JIUVdhOWswKw0KYnc4SEhhOHNIbzlnT2VMNk5sTVRPZFJlSml2YlBhZ1V2VExyR0FNb1VnUng1YXN6UGVFNHV3YzJoR0tjZWVvVw0KTVBSZndDdm9jV3ZrK1FJREFRQUJvMU13VVRBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUQQ0KZXBob2pZbjdxd1ZrREJGOXFuMWx1TXJNVGpBZkJnTlZIU01FR0RBV2dCVEFlcGhvalluN3F3VmtEQkY5cW4xbA0KdU1yTVRqQU5CZ2txaGtpRzl3MEJBUVVGQUFPQ0FRRUFOZU1wYXVVdlhWU09LVkNVbjVrYUZPU1BlQ3BpbEtJbg0KWjU3UXp4cGVSK25Cc3FUUDNVRWFCVTZiUys1S2IxVlNzeVNoTndyclpIWXFMaXp6L1R0MWtMLzZjZGpIUFRmUw0KdFFXVllybW0zb2s5Tm5zNGQwaVhyS1lnank2bXlRekNzcGxGQU1mT0VWRWlJdUNsNnJZVlNBbGs2bDVQZFBjRg0KUHNlS1VnemJGYlM5Ylp2bHhyRlVhS25qYVpDMm1xVVB1TGsvSUgydVNyVzRuT1FkdHF2bWxLWEJ4NE90Mi9Vbg0KaHc0RWJOWC8zYUJkN1lkU3R5c1ZBcTQ1cG1wMDZkckU1N3hOTkI2cFhFMHpYNUlKTDRobVhYZVh4eDEyRTZuVg0KNWZFV0NSRTExYXpiSkhGd0xKaFdDOWtYdE5IalVTdGVkZWpWME54UE5PM0NCV2FBb2N2bU13PT0NCi0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0NCg0KPC9jYT4NCg0KDQojIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjDQojIFRoZSBjbGllbnQgY2VydGlmaWNhdGUgZmlsZSAoZHVtbXkpLg0KIyANCiMgSW4gc29tZSBpbXBsZW1lbnRhdGlvbnMgb2YgT3BlblZQTiBDbGllbnQgc29mdHdhcmUNCiMgKGZvciBleGFtcGxlOiBPcGVuVlBOIENsaWVudCBmb3IgaU9TKSwNCiMgYSBwYWlyIG9mIGNsaWVudCBjZXJ0aWZpY2F0ZSBhbmQgcHJpdmF0ZSBrZXkgbXVzdCBiZSBpbmNsdWRlZCBvbiB0aGUNCiMgY29uZmlndXJhdGlvbiBmaWxlIGR1ZSB0byB0aGUgbGltaXRhdGlvbiBvZiB0aGUgY2xpZW50Lg0KIyBTbyB0aGlzIHNhbXBsZSBjb25maWd1cmF0aW9uIGZpbGUgaGFzIGEgZHVtbXkgcGFpciBvZiBjbGllbnQgY2VydGlmaWNhdGUNCiMgYW5kIHByaXZhdGUga2V5IGFzIGZvbGxvd3MuDQoNCjxjZXJ0Pg0KLS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlDeGpDQ0FhNENBUUF3RFFZSktvWklodmNOQVFFRkJRQXdLVEVhTUJnR0ExVUVBeE1SVmxCT1IyRjBaVU5zDQphV1Z1ZEVObGNuUXhDekFKQmdOVkJBWVRBa3BRTUI0WERURXpNREl4TVRBek5EazBPVm9YRFRNM01ERXhPVEF6DQpNVFF3TjFvd0tURWFNQmdHQTFVRUF4TVJWbEJPUjJGMFpVTnNhV1Z1ZEVObGNuUXhDekFKQmdOVkJBWVRBa3BRDQpNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDQVFFQTVoMmxnUVFZVWp3b0tZSmJ6VlpBDQo1VmNJR2Q1b3RQYy9xWlJNdDBLSXRDRkEwczlSd1JlTlZhOWZEUkZMUkJoY0lUT2x2M0ZCY1czRThoMVVzN1JEDQo0VzhHbUplOHphcEpuTHNEMzlPU01SQ3paSm5jelc0T0NIMVBaUlpXS3FEdGpsTmNhOUFGOGE2NWpUbWxEeENRDQpDam50TElXazVPTExWa0Z0OS90U2NjMUdEdGNpNTVvZmhhTkFZTVBpSDdWOCsxZzY2cEdIWEFvV0s2QVFWSDY3DQpYQ0tKbkdCNW5sUStIc01ZUFYvTzQ5TGQ5MVpOLzJ0SGtjYUxMeU50eXd4VlBSU3NSaDQ4MGpqdTBmY0NzdjZoDQpwLzB5WG5UQi8vbVd1dEJHcGRVbElid2lJVGJBbXJzYlluamlnUnZuUHFYMVJOSlViaTlGcDZDMmMvSElGSkdEDQp5d0lEQVFBQk1BMEdDU3FHU0liM0RRRUJCUVVBQTRJQkFRQ2hPNWhnY3cvNG9XZm9FRkx1OWtCYTFCLy9reEg4DQpoUWtDaFZObjhCUkM3WTBVUlFpdFBsM0RLRWVkOVVSQkRkZzJLT0F6NzdiYjZFTlBpbGlEK2EzOFVKSElSTXFlDQpVQkhobGxPSEl6dkRoSEZiYW92QUxCUWNlZUJ6ZGtReHNLUUVTS21RbVI4MzI5NTBVQ292b3lSQjYxVXlBVjdoDQorbVpoWVBHUktYS1NKSTZzMEVnZy9DcmkrQ3drNGJqSmZyYjVoVnNlMTF5aDREOU1IaHdTZkNPSCswejRoUFVUDQpGa3U3ZEdhdlVSTzVTVnhNbi9zTDZFbjVEK29TZVhrYWRIcERzK0FpcnltMllIaDE1aDAralBTT29SNnlpVnAvDQo2elplWmtyTjQza3VTNzNLcEtERmpmRlBoOHQ0cjFnT0lqdHRrTmNRcUJjY3VzbnBsUTdISnBzaw0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0KDQo8L2NlcnQ+DQoNCjxrZXk+DQotLS0tLUJFR0lOIFJTQSBQUklWQVRFIEtFWS0tLS0tDQpNSUlFcEFJQkFBS0NBUUVBNWgybGdRUVlVandvS1lKYnpWWkE1VmNJR2Q1b3RQYy9xWlJNdDBLSXRDRkEwczlSDQp3UmVOVmE5ZkRSRkxSQmhjSVRPbHYzRkJjVzNFOGgxVXM3UkQ0VzhHbUplOHphcEpuTHNEMzlPU01SQ3paSm5jDQp6VzRPQ0gxUFpSWldLcUR0amxOY2E5QUY4YTY1alRtbER4Q1FDam50TElXazVPTExWa0Z0OS90U2NjMUdEdGNpDQo1NW9maGFOQVlNUGlIN1Y4KzFnNjZwR0hYQW9XSzZBUVZINjdYQ0tKbkdCNW5sUStIc01ZUFYvTzQ5TGQ5MVpODQovMnRIa2NhTEx5TnR5d3hWUFJTc1JoNDgwamp1MGZjQ3N2NmhwLzB5WG5UQi8vbVd1dEJHcGRVbElid2lJVGJBDQptcnNiWW5qaWdSdm5QcVgxUk5KVWJpOUZwNkMyYy9ISUZKR0R5d0lEQVFBQkFvSUJBRVJWN1g1QXZ4QTh1UmlLDQprOFNJcHNEMGRYMXBKT01Jd2FrVVZ5dmM0RWZOMERoS1JOYjRyWW9TaUVHVEx5ekxweUJjL0EyOERsa201ZU9ZDQpmanpYZllrR3RZaS9GdHhrZzNPOXZjck1RNCs2aSt1R0hhSUwyckwrczRNcmZPOHYxeHY2K1dreTMzRUVHQ291DQpRaXdWR1JGUVhuUm9RNjJOQkNGYlVOTGhtWHdkajFha1p6TFU0cDVSNHpBM1FoZHh3RUlhdFZMdDArN293TFEzDQpsUDhzZlhocHBQT1hqVHFNRDRRa1l3elBBYTgvekY3YWNuNGtyeXJVUDdRNlBBZmQwekVWcU55OVpDWjlmZmhvDQp6WGVkRmo0ODZJRm9jNWduVHAyTjZqc25WajRMQ0dJaGxWSGxZR296S0tGcUpjUVZHc0hDcXExb3oyempXNkxTDQpvUllJSGdFQ2dZRUE4elpya0N3TllTWEp1T0RKM20vaE9MVnhjeGdKdXdYb2lFcldkMEU0MnZQYW5qalZNaG50DQpLWTVsOHFHTUo2RmhLOUxZeDJxQ3JmL0UwWHRVQVoyd1ZxM09SVHlHbnNNV3JlOXRMWXM1NVgrWk4xMFRjNzV6DQo0aGFjYlUwaHFLTjFIaURtc01SWTMvMk5hWkhveTdNS253SkpCYUc0OGw5Q0NUbFZ3TUhvY0lFQ2dZRUE4amJ5DQpkR2p4VEgrNlhIV05pemI1U1JiWnhBbnlFZUplUndUTWgwZ0d6d0dQcEgvc1pZR3p5dTBTeVNYV0NuWmgzUmdxDQo1dUxsTnh0clhybGpabHlpMm5RZFFnc3EyWXJXVXMwK3pnVSsyMnVRc1pwU0FmdG1oVnJ0dmV0Nk1qVmpiQnlZDQpEQURjaUVWVWRKWUlYaytxbkZVSnllcm9MSWtUajdXWUtaNlJqa3NDZ1lCb0NGSXdSRGVnNDJvSzg5UkZtbk9yDQpMeW1OQXE0KzJvTWhzV2xWYjRlaldJV2VBazluYytHWFVmclhzelJoUzAxbVVuVTVyNXlnVXZSY2FyVi9UM1U3DQpUbk1aK0k3WTREZ1dSSURkNTF6bmh4SUJ0WVY1ai9DL3Q4NUhqcU9rSCs4YjZSVGtiY2hhWDNtYXU3ZnBVZmRzDQpGcTBuaElxNDJmaEVPOHNyZllZd2dRS0JnUUN5aGkxTi84dGFSd3BrKzMvSURFelF3amJmZHpVa1dXU0RrOVhzDQpIL3BrdVJIV2ZUTVAzZmxXcUVZZ1cvTFc0MHBlVzJIRHE1aW1kVjgrQWdaeGUvWE1iYWppOUxnd2YxUlkwMDVuDQpLeGFaUXo3eXFIdXBXbExHRjY4RFBIeGtaVlZTYWdEblYvc3p0V1g2U0ZzQ3FGVm54SVhpZlhHQzRjVzVObTlnDQp2YThxNFFLQmdRQ0VoTFZlVWZkd0t2a1o5NGcvR0Z6NzMxWjJocmRWaGdNWmFVL3U2dDBWOTUrWWV6UE5DUVpCDQp3bUU5TW1sYnExZW1EZVJPaXZqQ2ZvR2hSM2taWFcxcFRLbExoNlpNVVFVT3BwdGRYdmE4WHhmb3FRd2EzZW5BDQpNN211QmJGMFhON1ZPODBpSlB2K1BtSVpkRUlBa3B3S2ZpMjAxWUIrQmFmQ0l1R3hJRjUwVmc9PQ0KLS0tLS1FTkQgUlNBIFBSSVZBVEUgS0VZLS0tLS0NCg0KPC9rZXk+DQoNCg=="));
}
	
private static char[]    map1 = new char[64];
   static {
      int i=0;
      for (char c='A'; c<='Z'; c++) map1[i++] = c;
      for (char c='a'; c<='z'; c++) map1[i++] = c;
      for (char c='0'; c<='9'; c++) map1[i++] = c;
      map1[i++] = '+'; map1[i++] = '/'; }

// Mapping table from Base64 characters to 6-bit nibbles.
private static byte[]    map2 = new byte[128];
   static {
      for (int i=0; i<map2.length; i++) map2[i] = -1;
      for (int i=0; i<64; i++) map2[map1[i]] = (byte)i; }

/**
* Encodes a string into Base64 format.
* No blanks or line breaks are inserted.
* @param s  a String to be encoded.
* @return   A String with the Base64 encoded data.
*/
public static String encodeString (String s) {
   return new String(encode(s.getBytes())); }

/**
* Encodes a byte array into Base64 format.
* No blanks or line breaks are inserted.
* @param in  an array containing the data bytes to be encoded.
* @return    A character array with the Base64 encoded data.
*/
public static char[] encode (byte[] in) {
   return encode(in,in.length); }

/**
* Encodes a byte array into Base64 format.
* No blanks or line breaks are inserted.
* @param in   an array containing the data bytes to be encoded.
* @param iLen number of bytes to process in <code>in</code>.
* @return     A character array with the Base64 encoded data.
*/
public static char[] encode (byte[] in, int iLen) {
   int oDataLen = (iLen*4+2)/3;       // output length without padding
   int oLen = ((iLen+2)/3)*4;         // output length including padding
   char[] out = new char[oLen];
   int ip = 0;
   int op = 0;
   while (ip < iLen) {
      int i0 = in[ip++] & 0xff;
      int i1 = ip < iLen ? in[ip++] & 0xff : 0;
      int i2 = ip < iLen ? in[ip++] & 0xff : 0;
      int o0 = i0 >>> 2;
      int o1 = ((i0 &   3) << 4) | (i1 >>> 4);
      int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
      int o3 = i2 & 0x3F;
      out[op++] = map1[o0];
      out[op++] = map1[o1];
      out[op] = op < oDataLen ? map1[o2] : '='; op++;
      out[op] = op < oDataLen ? map1[o3] : '='; op++; }
   return out; }

/**
* Decodes a string from Base64 format.
* @param s  a Base64 String to be decoded.
* @return   A String containing the decoded data.
* @throws   IllegalArgumentException if the input is not valid Base64 encoded data.
*/
public static String decodeString (String s) {
   return new String(decode(s)); }

/**
* Decodes a byte array from Base64 format.
* @param s  a Base64 String to be decoded.
* @return   An array containing the decoded data bytes.
* @throws   IllegalArgumentException if the input is not valid Base64 encoded data.
*/
public static byte[] decode (String s) {
   return decode(s.toCharArray()); }

/**
* Decodes a byte array from Base64 format.
* No blanks or line breaks are allowed within the Base64 encoded data.
* @param in  a character array containing the Base64 encoded data.
* @return    An array containing the decoded data bytes.
* @throws    IllegalArgumentException if the input is not valid Base64 encoded data.
*/
public static byte[] decode (char[] in) {
   int iLen = in.length;
   if (iLen%4 != 0) throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
   while (iLen > 0 && in[iLen-1] == '=') iLen--;
   int oLen = (iLen*3) / 4;
   byte[] out = new byte[oLen];
   int ip = 0;
   int op = 0;
   while (ip < iLen) {
      int i0 = in[ip++];
      int i1 = in[ip++];
      int i2 = ip < iLen ? in[ip++] : 'A';
      int i3 = ip < iLen ? in[ip++] : 'A';
      if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
         throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
      int b0 = map2[i0];
      int b1 = map2[i1];
      int b2 = map2[i2];
      int b3 = map2[i3];
      if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
         throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
      int o0 = ( b0       <<2) | (b1>>>4);
      int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
      int o2 = ((b2 &   3)<<6) |  b3;
      out[op++] = (byte)o0;
      if (op<oLen) out[op++] = (byte)o1;
      if (op<oLen) out[op++] = (byte)o2; }
   return out; }

// Dummy constructor.
private Base64Coder() {}

} // end class Base64Coder
