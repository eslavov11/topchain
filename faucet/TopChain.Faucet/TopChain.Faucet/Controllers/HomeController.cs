namespace TopChain.Faucet.Controllers
{
    using System;
    using System.Diagnostics;
    using Microsoft.AspNetCore.Mvc;
    using System.Net.Http;
    using System.Text;

    public class HomeController : Controller
    {

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Error()
        {
            ViewData["RequestId"] = Activity.Current?.Id ?? HttpContext.TraceIdentifier;
            return View();
        }

        [HttpPost]
        [Route("api/Home/ReturnOnSuccess")]
        public string ReturnOnSuccess([FromBody] string addressToReceiveMoney)
        {
            string iso8601standard = DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
            string signedTran = TransactionSender.CreateAndSignTransaction(addressToReceiveMoney, 1, iso8601standard);
            TransactionSender.SendSignedTransaction(signedTran);
            string tranHash = TransactionSender.BytesToHex(TransactionSender.TranHash);
            return tranHash;
        }

       
    }
}
